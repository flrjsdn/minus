package com.hexa.muinus.store.service;

import com.hexa.muinus.common.exception.api.item.ItemNotFoundException;
import com.hexa.muinus.common.exception.api.item.StoreItemNotFoundException;
import com.hexa.muinus.store.domain.item.Item;
import com.hexa.muinus.store.domain.item.StoreItem;
import com.hexa.muinus.store.domain.item.repository.StoreItemRepository;
import com.hexa.muinus.store.domain.store.Store;
import com.hexa.muinus.store.dto.store.StoreItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreItemService {

    private final StoreItemRepository storeItemRepository;

    @Transactional(readOnly = true)
    public StoreItem findStoreItemByStoreAndItem(Store store, Item item) {
        return storeItemRepository.findByStoreAndItem(store, item)
                .orElseThrow(() -> new StoreItemNotFoundException(store.getStoreNo(), item.getItemId()));
    }

    @Transactional(readOnly = true)
    public StoreItem findStoreItemByStore(Store store) {
        return storeItemRepository.findByStore(store)
                .orElseThrow(ItemNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<StoreItemDTO> findAllStoreItems(int storeNo) {
        return storeItemRepository.findStoreItemsByStoreNo(storeNo);
    }
}
