package com.hexa.muinus.elasticsearch.controller;

import com.hexa.muinus.elasticsearch.domain.ESItem;
import com.hexa.muinus.elasticsearch.service.ESItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ESItemController {

    private final ESItemService esItemService;

    @GetMapping("/items/autocomplete")
    public List<ESItem> autocomplete(@RequestParam String prefix) {
        return esItemService.autocompleteItemName(prefix);
    }

    @GetMapping("/items/search")
    public List<ESItem> searchByRange(@RequestParam(required = false) Integer minSugar,
                                      @RequestParam(required = false) Integer maxSugar,
                                      @RequestParam(required = false) Integer minCal,
                                      @RequestParam(required = false) Integer maxCal) {
        return esItemService.searchBySugarAndCalorieRange(minSugar, maxSugar, minCal, maxCal);
    }

    @GetMapping("/items/search.keyword")
    public List<ESItem> searchByQuery(@RequestParam("query") String query,
                                      @RequestParam(name = "minSugar", required = false) Integer minSugar,
                                      @RequestParam(name = "maxSugar", required = false) Integer maxSugar,
                                      @RequestParam(name = "minCal", required = false) Integer minCal,
                                      @RequestParam(name = "maxCal", required = false) Integer maxCal,
                                      @RequestParam(name = "brand", required = false) String brand) {
        log.info("Search By Query: {}", query);
        return esItemService.searchByQuery(query, minSugar, maxSugar, minCal, maxCal, brand);
    }
}

