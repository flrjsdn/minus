package com.hexa.muinus.store.service;

import com.hexa.muinus.store.domain.item.FliItem;
import com.hexa.muinus.store.domain.item.repository.FliItemRepository;
import com.hexa.muinus.store.domain.store.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class FliItemService {

    private final FliItemRepository fliItemRepository;

    @Transactional
    public FliItem findFliItemByStoreAndSectionId(Store store, Integer sectionId) {
        return fliItemRepository.findByStoreAndSectionId(store, sectionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 상품입니다."));
    }

    @Transactional
    public FliItem findFliItemByStoreAndFliItemId(Store store, Integer fliItemId) {
        return fliItemRepository.findByStoreAndFliItemId(store, fliItemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 상품입니다."));
    }

}
