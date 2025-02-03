package com.hexa.muinus.store.domain.item.repository;

import com.hexa.muinus.store.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}
