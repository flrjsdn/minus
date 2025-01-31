package com.hexa.muinus.elasticsearch.service;

import com.hexa.muinus.elasticsearch.domain.ESItem;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.springframework.data.domain.PageRequest;
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

    private final ElasticsearchOperations elasticsearchTemplate;

    /**
     * Prefix 쿼리를 사용하여 itemName 자동완성 (상위 5개 추천)
     *
     * @param prefix 자동완성을 위한 접두사
     * @return 자동완성된 ESItem 목록
     */
    public List<ESItem> autocompleteItemName(String prefix) {
        // Prefix 쿼리 생성
        BoolQueryBuilder prefixQuery = QueryBuilders.boolQuery()
                .must(QueryBuilders.prefixQuery("itemName", prefix));

        // 검색 쿼리 빌드 (상위 5개만)
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(prefixQuery)
                .withPageable(PageRequest.of(0, 5))
                .build();

        // 검색 실행
        SearchHits<ESItem> searchHits = elasticsearchTemplate.search(searchQuery, ESItem.class);

        // 검색 결과에서 ESItem 목록 추출
        return searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

    /**
     * 당(sugars)과 칼로리(calories)의 범위로 검색
     *
     * @param minSugar 최소 당 함량
     * @param maxSugar 최대 당 함량
     * @param minCal   최소 칼로리
     * @param maxCal   최대 칼로리
     * @return 해당 범위에 맞는 ESItem 목록
     */
    public List<ESItem> searchBySugarAndCalorieRange(Integer minSugar,
                                                     Integer maxSugar,
                                                     Integer minCal,
                                                     Integer maxCal) {

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        // 당(sugars) 범위 쿼리 추가
        if (minSugar != null || maxSugar != null) {
            RangeQueryBuilder sugarRangeQuery = QueryBuilders.rangeQuery("sugars");
            if (minSugar != null) {
                sugarRangeQuery.gte(minSugar);
            } else {
                sugarRangeQuery.gte(0);
            }
            if (maxSugar != null) {
                sugarRangeQuery.lte(maxSugar);
            } else {
                sugarRangeQuery.lte(Integer.MAX_VALUE);
            }
            boolQuery.must(sugarRangeQuery);
        }

        // 칼로리(calories) 범위 쿼리 추가
        if (minCal != null || maxCal != null) {
            RangeQueryBuilder calRangeQuery = QueryBuilders.rangeQuery("calories");
            if (minCal != null) {
                calRangeQuery.gte(minCal);
            } else {
                calRangeQuery.gte(0);
            }
            if (maxCal != null) {
                calRangeQuery.lte(maxCal);
            } else {
                calRangeQuery.lte(Integer.MAX_VALUE);
            }
            boolQuery.must(calRangeQuery);
        }

        // 검색 쿼리 빌드 (페이징 없이 모든 결과, 페이지 사이즈 조정 가능)
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQuery)
                .withPageable(PageRequest.of(0, 1000)) // 필요에 따라 페이지 사이즈 조정
                .build();

        // 검색 실행
        SearchHits<ESItem> searchHits = elasticsearchTemplate.search(searchQuery, ESItem.class);

        // 검색 결과에서 ESItem 목록 추출
        return searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }
}
