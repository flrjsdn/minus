package com.hexa.muinus.elasticsearch.service;

import com.hexa.muinus.elasticsearch.domain.ESItem;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ESItemService {

    private final ElasticsearchOperations elasticsearchOperations;

    /**
     * 1) prefix query 로 5개 itemName 추천
     */
    public List<ESItem> autocompleteItemName(String prefix) {
        // 1) prefix query
        // 예) itemName 필드가 prefix로 시작하는 것 검색
        var prefixQuery = QueryBuilders.prefixQuery("item_name", prefix);

        // 2) NativeSearchQueryBuilder
        Pageable limit = PageRequest.of(0, 5); // 상위 5개만
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(prefixQuery)
                .withPageable(limit)
                .build();

        // 3) 검색 실행
        SearchHits<ESItem> searchHits = elasticsearchOperations.search(searchQuery, ESItem.class);

        // 4) itemName 만 반환
        return searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

    /**
     * 2) 당(sugars)과 칼로리(calories) 범위 검색
     */
    public List<ESItem> searchBySugarAndCalorieRange(Integer minSugar,
                                                     Integer maxSugar,
                                                     Integer minCal,
                                                     Integer maxCal) {

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        // sugars 범위
        if (minSugar != null || maxSugar != null) {
            boolQuery.must(QueryBuilders.rangeQuery("sugars")
                    .gte(minSugar != null ? minSugar : 0)
                    .lte(maxSugar != null ? maxSugar : Integer.MAX_VALUE));
        }

        // calories 범위
        if (minCal != null || maxCal != null) {
            boolQuery.must(QueryBuilders.rangeQuery("calories")
                    .gte(minCal != null ? minCal : 0)
                    .lte(maxCal != null ? maxCal : Integer.MAX_VALUE));
        }

        // 쿼리 생성
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQuery)
                .build();

        // 검색 실행
        SearchHits<ESItem> searchHits = elasticsearchOperations.search(searchQuery, ESItem.class);

        // 실제 문서만 추출
        return searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }
}
