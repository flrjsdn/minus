package com.hexa.muinus.elasticsearch.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.IntegerNumberProperty;
import co.elastic.clients.elasticsearch._types.mapping.KeywordProperty;
import co.elastic.clients.elasticsearch._types.mapping.TextProperty;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.ExistsRequest;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;


import com.hexa.muinus.store.domain.item.Item;
import com.hexa.muinus.store.domain.item.Repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final ElasticsearchClient esClient;
    private final ItemRepository itemRepository;

    private static final String INDEX_NAME = "item_index";

    /**
     * 인덱스가 없으면 생성 (예시)
     */
    public void createIndexIfNotExist() throws IOException {
        // 인덱스 존재 여부 확인
        BooleanResponse existsResponse = esClient.indices().exists(ExistsRequest.of(e -> e.index(INDEX_NAME)));
        if (existsResponse.value()) {
            return;
        }

        // 인덱스 생성
        CreateIndexRequest createIndexRequest = CreateIndexRequest.of(builder -> builder
                .index(INDEX_NAME)
                .settings(s -> s
                        .analysis(a -> a
                                .analyzer("nori_token", analz -> analz
                                        .custom(customAnalyzer -> customAnalyzer
                                                .tokenizer("nori_tokenizer"))
                                )
                        )
                )
                .mappings(m -> m
                        .properties("itemId", p -> p.integer(IntegerNumberProperty.of(i -> i)))
                        .properties("barcode", p -> p.keyword(KeywordProperty.of(k -> k)))
                        .properties("itemName", p -> p.text(TextProperty.of(t -> t.analyzer("nori_token"))))
                        .properties("brand", p -> p.text(TextProperty.of(t -> t.analyzer("nori_token"))))
                        .properties("calories", p -> p.integer(IntegerNumberProperty.of(i -> i)))
                        .properties("protein", p -> p.integer(IntegerNumberProperty.of(i -> i)))
                        .properties("fat", p -> p.integer(IntegerNumberProperty.of(i -> i)))
                        .properties("carbohydrate", p -> p.integer(IntegerNumberProperty.of(i -> i)))
                        .properties("sugars", p -> p.integer(IntegerNumberProperty.of(i -> i)))
                        .properties("weight", p -> p.integer(IntegerNumberProperty.of(i -> i)))
                        .properties("itemImageUrl", p -> p.text(TextProperty.of(t -> t)))
                )
        );

        CreateIndexResponse createIndexResponse = esClient.indices().create(createIndexRequest);
        if (createIndexResponse.acknowledged()) {
            System.out.println("인덱스 생성 완료: " + INDEX_NAME);
        } else {
            System.out.println("인덱스 생성 실패: " + INDEX_NAME);
        }
    }

    /**
     * MySQL에 있는 전체 아이템을 ES에 색인 (간단 버전)
     */
    @Transactional(readOnly = true)
    public void indexAllItems() throws IOException {
        List<Item> items = itemRepository.findAll();

        for (Item item : items) {
            // docId를 itemId로
            IndexResponse response = esClient.index(i -> i
                    .index(INDEX_NAME)
                    .id(String.valueOf(item.getItemId()))
                    .document(item)
            );
            System.out.println("색인 완료: " + response.id());
        }
    }

    /**
     * 검색 기능 (예: itemName, brand 필드에 대해 match 검색)
     */
    public List<Item> searchItems(String keyword) throws IOException {
        // Elasticsearch 결과를 다시 ItemEntity 구조에 담고 싶다면,
        // ES용 전용 DTO를 사용하거나, 여기서는 편의상 ItemEntity 자체를 재사용한다고 가정.

        SearchResponse<Item> searchResponse = esClient.search(s -> s
                        .index(INDEX_NAME)
                        .query(q -> q
                                .multiMatch(m -> m
                                        .fields("itemName", "brand")
                                        .query(keyword)
                                )
                        )
                        .size(50),
                Item.class
        );

        return searchResponse.hits().hits().stream()
                .map(Hit::source)
                .toList();
    }
}
