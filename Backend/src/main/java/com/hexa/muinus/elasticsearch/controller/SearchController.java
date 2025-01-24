package com.hexa.muinus.elasticsearch.controller;

import com.hexa.muinus.elasticsearch.service.SearchService;
import com.hexa.muinus.store.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;

    /**
     * 인덱스 없으면 생성
     */
    @PostMapping("/create-index")
    public String createIndex() throws IOException {
        searchService.createIndexIfNotExist();
        return "Index created if not existed.";
    }

    /**
     * DB -> ES 전체 색인
     */
    @PostMapping("/index-all")
    public String indexAll() throws IOException {
        searchService.indexAllItems();
        return "All items indexed.";
    }

    /**
     * 검색
     */
    @GetMapping
    public List<Item> search(@RequestParam("keyword") String keyword) throws IOException {
        return searchService.searchItems(keyword);
    }
}
