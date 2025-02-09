package com.hexa.muinus.elasticsearch.temp;

import com.hexa.muinus.elasticsearch.domain.ESItem;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SearchController {

    private final ItemNameSearchEngine searchEngine;

    @GetMapping("/items/search-native")
    public List<ESItem> searchByQuery(@Valid @ModelAttribute SearchNativeDTO searchNativeDTO) {
        log.info("Search Item: {}", searchNativeDTO);
        return searchEngine.searchByQuery(searchNativeDTO);
    }

}
