package com.hexa.muinus.elasticsearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.core.convert.MappingElasticsearchConverter;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext;

@Configuration
public class ElasticSearchConfig {

    /**
     * application.properties 또는 application.yml에서 Elasticsearch 호스트와 포트를 주입받습니다.
     */
    @Value("${elasticsearch.host:localhost}")
    private String elasticsearchHost;

    @Value("${elasticsearch.port:9200}")
    private int elasticsearchPort;

    /**
     * 1) RestHighLevelClient를 빈으로 등록합니다.
     *
     * @return RestHighLevelClient 인스턴스
     */
    @Bean
    public RestHighLevelClient client() {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(elasticsearchHost, elasticsearchPort, "http")
                )
        );
    }

    /**
     * 2) ElasticsearchConverter를 설정합니다.
     *
     * @return ElasticsearchConverter 인스턴스
     */
    @Bean
    public ElasticsearchConverter elasticsearchConverter() {
        return new MappingElasticsearchConverter(elasticsearchMappingContext());
    }

    /**
     * 3) ElasticsearchMappingContext를 설정합니다.
     *
     * @return SimpleElasticsearchMappingContext 인스턴스
     */
    @Bean
    public SimpleElasticsearchMappingContext elasticsearchMappingContext() {
        return new SimpleElasticsearchMappingContext();
    }

    /**
     * 4) ElasticsearchTemplate을 빈으로 등록합니다.
     *
     * @param client     RestHighLevelClient
     * @param converter ElasticsearchConverter
     * @return ElasticsearchOperations 인스턴스
     */
    @Bean
    public ElasticsearchOperations elasticsearchTemplate(RestHighLevelClient client, ElasticsearchConverter converter) {
        return new ElasticsearchRestTemplate(client, converter);
    }
}
