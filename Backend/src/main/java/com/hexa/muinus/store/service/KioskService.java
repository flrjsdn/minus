package com.hexa.muinus.store.service;

import com.hexa.muinus.store.domain.item.Item;
import com.hexa.muinus.store.domain.item.repository.ItemRepository;
import com.hexa.muinus.store.domain.item.repository.StoreItemRepository;
import com.hexa.muinus.store.dto.ScanBarcodeRequestDTO;
import com.hexa.muinus.store.dto.ScanBarcodeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class KioskService {

    private final StoreItemRepository storeItemRepository;
    private final ItemRepository itemRepository;

    public ScanBarcodeResponseDTO scanBarcode(Integer storeNo, String barcode) {
        // 바코드 번호로 item 테이블에서 상품 번호 및 이름 조회
        Item item = itemRepository.findItemByBarcode(barcode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 바코드 번호입니다."));

        // storeNo, itemNo로 store_item 테이블에서 상품 가격 조회
        Integer price = storeItemRepository.getPriceByStoreNoAndItemNo(storeNo, item.getItemId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "판매하지 않는 상품입니다."));

        return ScanBarcodeResponseDTO.builder()
                .itemName(item.getItemName())
                .price(price)
                .build();
    }
}
