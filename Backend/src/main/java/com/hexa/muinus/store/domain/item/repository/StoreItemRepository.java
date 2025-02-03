package com.hexa.muinus.store.domain.item.repository;

import com.hexa.muinus.store.domain.item.StoreItem;
import com.hexa.muinus.store.dto.StoreItemDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreItemRepository extends CrudRepository<StoreItem, Integer> {

    @Query("""
    SELECT new com.hexa.muinus.store.dto.StoreItemDTO(
        si.storeItemId, i.itemId, i.itemName, i.itemImageUrl, 
        si.quantity, si.salePrice, si.discountRate, (si.salePrice * (100 - si.discountRate) / 100)
    )
    FROM StoreItem si
    JOIN si.item i
    WHERE si.store.storeNo = :storeNo
""")
    List<StoreItemDTO> findStoreItemsByStore(@Param("storeNo") int storeNo);
}
