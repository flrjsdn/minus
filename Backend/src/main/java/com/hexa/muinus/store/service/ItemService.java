package com.hexa.muinus.store.service;

import com.hexa.muinus.store.dto.ItemDto;
import com.hexa.muinus.store.domain.item.repository.ItemRepository;
import com.hexa.muinus.store.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    /**
     * 상품 등록
     * @param item
     * @return
     */
    public Item createItem(ItemDto item) {
        return itemRepository.save(item.toEntity());
    }

    /**
     * 상품 수정
     * @param id
     * @param updatedItem
     * @return
     */
    public Item updateItem(Integer id, ItemDto updatedItem) {

        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));

        item.setBarcode(updatedItem.getBarcode());
        item.setItemName(updatedItem.getItemName());
        item.setBrand(updatedItem.getBrand());
        item.setCalories(updatedItem.getCalories());
        item.setProtein(updatedItem.getProtein());
        item.setFat(updatedItem.getFat());
        item.setCarbohydrate(updatedItem.getCarbohydrate());
        item.setSugars(updatedItem.getSugars());
        item.setWeight(updatedItem.getWeight());
        item.setItemImageUrl(updatedItem.getItemImageUrl());

        return itemRepository.save(item);
    }

    /**
     * 상품 삭제
     * @param id
     */
    public void deleteItem(Integer id) {
        itemRepository.deleteById(id);
    }

    /**
     * 상품 조회
     * @param id
     * @return 해당 id 상품
     */
    public Item getItem(Integer id) {
        Optional<Item> item = itemRepository.findById(id);
        return item.orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));
    }

    /**
     * 전체 상품 조회
     * @return 모든 아이템 리스트
     */
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
}

