package com.hexa.muinus.store.domain.store.repository;

import com.hexa.muinus.store.domain.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Integer> {
    // 가게 이름으로 store_no 조회
    @Query("SELECT s.storeNo FROM Store s WHERE s.name = :storeName")
    Optional<Integer> findStoreNoByName(@Param("storeName") String storeName);
}
