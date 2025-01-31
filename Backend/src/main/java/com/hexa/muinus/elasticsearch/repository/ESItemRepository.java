package com.hexa.muinus.elasticsearch.repository;

import com.hexa.muinus.elasticsearch.domain.ESItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ESItemRepository extends ElasticsearchRepository<ESItem, Integer> {

    List<ESItem> findByItemNameStartingWith(String prefix);
}
