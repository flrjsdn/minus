package com.hexa.muinus.store.domain.item.repository;

import com.hexa.muinus.store.domain.item.FliItem;
import com.hexa.muinus.store.dto.FliItemDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FliItemRepository extends CrudRepository<FliItem, Integer> {
    @Query("""
    SELECT new com.hexa.muinus.store.dto.FliItemDTO(
        fi.fliItemId, u.userNo, fi.fliItemName, fi.price, fi.quantity
    )
    FROM FliItem fi
    JOIN fi.users u
    WHERE fi.store.storeNo = :storeNo
    AND fi.status = 'SELLING'
""")
    List<FliItemDTO> findSellingFliItemsByStore(@Param("storeNo") int storeNo);
}
