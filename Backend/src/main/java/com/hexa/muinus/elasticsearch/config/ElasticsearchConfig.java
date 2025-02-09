package com.hexa.muinus.elasticsearch.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.core.convert.MappingElasticsearchConverter;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

@Configuration
public class ElasticsearchConfig {

    @Value("${spring.elasticsearch.rest.uris}")
    private String esUri;

    @Value("${spring.elasticsearch.rest.username:}")
    private String username;

    @Value("${spring.elasticsearch.rest.password:}")
    private String password;

    /**
     * RestClient 생성
     * - 인증정보가 있으면 Basic 인증을 적용합니다.
     */
    @Bean
    public RestClient restClient() {
        RestClientBuilder builder = RestClient.builder(HttpHost.create(esUri));
        if (username != null && !username.isEmpty()) {
            BasicCredentialsProvider credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(
                    AuthScope.ANY,
                    new UsernamePasswordCredentials(username, password)
            );
            builder.setHttpClientConfigCallback(
                    httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credsProvider)
            );
        }
        return builder.build();
    }

    /**
     * ElasticsearchClient 생성
     * - RestClient를 기반으로 RestClientTransport와 JacksonJsonpMapper를 사용합니다.
     */
    @Bean
    public ElasticsearchClient elasticsearchClient(RestClient restClient) {
        RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        return new ElasticsearchClient(transport);
    }

    /**
     * 도메인-문서 매핑을 위한 MappingContext 생성
     */
    @Bean
    public SimpleElasticsearchMappingContext mappingContext() {
        return new SimpleElasticsearchMappingContext();
    }

    /**
     * ElasticsearchConverter 생성
     * - 도메인 객체와 Elasticsearch 문서 간의 변환을 지원합니다.
     */
    @Bean
    public ElasticsearchConverter elasticsearchConverter(SimpleElasticsearchMappingContext mappingContext) {
        return new MappingElasticsearchConverter(mappingContext);
    }

    @Bean
    public GenericConversionService conversionService() {
        GenericConversionService conversionService = new GenericConversionService();
        // 커스텀 변환기 등록
        conversionService.addConverter(new StringToLocalDateTimeConverter());
        return conversionService;
    }

    /**
     * ElasticsearchOperations 빈 생성
     * - 새로운 ElasticsearchTemplate은 org.springframework.data.elasticsearch.client.elc 패키지에 있습니다.
     */
    @Bean("elasticsearchTemplate")
    public ElasticsearchOperations elasticsearchOperations(ElasticsearchClient client, ElasticsearchConverter converter) {
        return new ElasticsearchTemplate(client, converter);
    }
}
