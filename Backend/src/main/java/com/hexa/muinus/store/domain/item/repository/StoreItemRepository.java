package com.hexa.muinus.store.domain.item.repository;

import com.hexa.muinus.store.domain.item.Item;
import com.hexa.muinus.store.domain.item.StoreItem;
import com.hexa.muinus.store.domain.store.Store;
import com.hexa.muinus.store.dto.StoreItemDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

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

    Optional<StoreItem> findByStoreAndItem(Store store, Item item);

    Optional<StoreItem> findByStore(Store store);
}
