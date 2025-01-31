package com.hexa.muinus.elasticsearch.controller;

import com.hexa.muinus.elasticsearch.domain.ESItem;
import com.hexa.muinus.elasticsearch.service.ESItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}

