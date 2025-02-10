package com.hexa.muinus.elasticsearch.controller;

import com.hexa.muinus.elasticsearch.domain.ESItem;
import com.hexa.muinus.elasticsearch.domain.ESStoreItem;
import com.hexa.muinus.elasticsearch.service.ESItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ESItemController {

    private final ESItemService esItemService;

    @GetMapping("/autocomplete")
    public List<ESItem> autocomplete(@RequestParam String prefix) {
        return esItemService.autocompleteItemName(prefix);
    }

    @GetMapping("/search")
    public List<ESItem> searchByRange(@RequestParam(required = false) Integer minSugar,
                                      @RequestParam(required = false) Integer maxSugar,
                                      @RequestParam(required = false) Integer minCal,
                                      @RequestParam(required = false) Integer maxCal) {
        return esItemService.searchBySugarAndCalorieRange(minSugar, maxSugar, minCal, maxCal);
    }

    /**
     * 특정 아이템(itemId)과 기준 좌표(lat, lon)를 받아 반경 1km 내의 매장을 검색합니다.
     *
     * @param itemId  아이템 ID
     * @param lat     기준 위도
     * @param lon     기준 경도
     * @return        검색된 매장 리스트
     */
    @GetMapping("/store-items")
    public ResponseEntity<List<ESStoreItem>> searchStoreItems(
            @RequestParam("itemId") Integer itemId,
            @RequestParam("lat") double lat,
            @RequestParam("lon") double lon) {

        List<ESStoreItem> results = esItemService.searchStoreItemsByRange(itemId, lat, lon);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/store-list")
    public ResponseEntity<List<ESStoreItem>> searchStore(
            @RequestParam("lat") double lat,
            @RequestParam("lon") double lon) {

        List<ESStoreItem> results = esItemService.searchStoreByRange(lat, lon);
        return ResponseEntity.ok(results);
    }
}

