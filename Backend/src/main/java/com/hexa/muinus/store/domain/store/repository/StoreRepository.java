package com.hexa.muinus.store.domain.store.repository;

import com.hexa.muinus.store.domain.store.Store;
import com.hexa.muinus.store.dto.StoreDTO;
import com.hexa.muinus.store.dto.StoreSearchDTO;
import com.hexa.muinus.users.domain.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Integer> {
    // 가게 이름으로 store_no 조회
    @Query("SELECT s.storeNo FROM Store s WHERE s.name = :storeName")
    Optional<Integer> findStoreNoByName(@Param("storeName") String storeName);

    Optional<Store> findByLocationXAndLocationY(BigDecimal locationX, BigDecimal locationY);
    Optional<Store> findByUserAndStoreNo(Users user, Integer storeNo);

    @Query(value = """
            SELECT new com.hexa.muinus.store.dto.StoreSearchDTO(
                s.storeNo, s.name, s.locationX, s.locationY, 
                s.address, s.phone, i.itemName, si.salePrice, 
                si.discountRate, (si.salePrice * (100 - si.discountRate) / 100), 
                si.quantity, s.flimarketYn
            )
            FROM Store s
            JOIN StoreItem si ON s.storeNo = si.store.storeNo
            JOIN Item i ON si.item.itemId = i.itemId
            WHERE i.itemId = :itemId
        """)
    List<StoreSearchDTO> findStoresByItemId(@Param("itemId") Integer itemId);

    @Query("""
        SELECT new com.hexa.muinus.store.dto.StoreDTO(
            s.storeNo, s.user.userNo, s.name, 
            s.address, s.storeImageUrl, s.phone, s.flimarketYn
        )
        FROM Store s
        WHERE s.storeNo = :storeNo
    """)
    Optional<StoreDTO> findStoreDTOById(@Param("storeNo") int storeNo);

    @Modifying
    @Query(value = "INSERT INTO store (user_no, name, location, address, registration_no, flimarket_yn, flimarket_section_cnt) " +
            "VALUES (:userNo, :name, POINT(:longitude, :latitude), :address, :registrationNo, :flimarketYn, :fliMarketSectionCount)", nativeQuery = true)
    int saveStore(Integer userNo, String name, Double longitude, Double latitude, String address, String registrationNo, String flimarketYn, Byte fliMarketSectionCount);
}
