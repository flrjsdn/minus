//package com.hexa.muinus.elasticsearch.service;
//
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.client.indices.CreateIndexRequest;
//import org.elasticsearch.client.indices.GetIndexRequest;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//
//@Service
//@RequiredArgsConstructor
//public class IndexService {
//    private final RestHighLevelClient client;
//
//    @PostConstruct
//    public void createIndexIfNotExists() throws IOException {
//        GetIndexRequest request = new GetIndexRequest("items");
//        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
//
//        if (!exists) {
//            CreateIndexRequest createIndexRequest = new CreateIndexRequest("items");
//            createIndexRequest.settings(Map.of(
//                    "analysis", Map.of(
//                            "analyzer", Map.of(
//                                    "nori_analyzer", Map.of(
//                                            "type", "custom",
//                                            "tokenizer", "nori_tokenizer",
//                                            "filter", List.of("lowercase", "synonym_filter")
//                                    )
//                            )
//                    )
//            ));
//
//            createIndexRequest.mapping(Map.of(
//                    "properties", Map.of(
//                            "item_name", Map.of(
//                                    "type", "text",
//                                    "analyzer", "nori_analyzer"
//                            )
//                    )
//            ));
//
//            client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
//        }
//    }
//}
