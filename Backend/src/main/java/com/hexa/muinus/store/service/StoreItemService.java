package com.hexa.muinus.store.service;

import com.hexa.muinus.store.domain.item.Item;
import com.hexa.muinus.store.domain.item.StoreItem;
import com.hexa.muinus.store.domain.item.repository.StoreItemRepository;
import com.hexa.muinus.store.domain.store.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class StoreItemService {

    private final StoreItemRepository storeItemRepository;

    @Transactional(readOnly = true)
    public StoreItem findStoreItemByStoreAndItem(Store store, Item item) {
        return storeItemRepository.findByStoreAndItem(store, item)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "판매하지 않는 상품입니다."));
    }

    @Transactional(readOnly = true)
    public StoreItem findStoreItemByStore(Store store) {
        return storeItemRepository.findByStore(store)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 상품입니다."));
    }
}
