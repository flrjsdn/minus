package com.hexa.muinus.users.domain.favorite.repository;

import com.hexa.muinus.users.domain.favorite.Favorites;
import com.hexa.muinus.users.domain.favorite.FavoritesId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorites, FavoritesId> {
    //특정 사용자의 즐겨찾기 목록 조회
    @Query("SELECT f.id.storeNo FROM Favorites f WHERE f.id.userNo = :userNo")
    List<Integer> findStoreNoListByUser(@Param("userNo") Integer userNo);

    //특정 사용자의 즐겨찾기 삭제
    @Modifying
    @Transactional
    @Query("DELETE FROM Favorites f WHERE f.id.userNo = :userNo AND f.id.storeNo = :storeNo")
    void deleteByUserNoAndStoreNo(@Param("userNo") Integer userNo, @Param("storeNo") Integer storeNo);
}