package com.hexa.muinus.users.service;

import com.hexa.muinus.store.domain.store.repository.StoreRepository;
import com.hexa.muinus.store.domain.store.Store;
import com.hexa.muinus.users.domain.favorite.Favorites;
import com.hexa.muinus.users.domain.favorite.FavoritesId;
import com.hexa.muinus.users.dto.FavoriteResponseDto;
import com.hexa.muinus.users.domain.favorite.repository.FavoriteRepository;
import com.hexa.muinus.users.domain.user.Users;
import com.hexa.muinus.users.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    public void addFavorite(Integer userNo, Integer storeNo){
        Users user = userRepository.findById(userNo)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Store store = storeRepository.findById(storeNo)
                .orElseThrow(() -> new IllegalArgumentException("Store not found"));

        FavoritesId favoritesId = new FavoritesId(userNo, storeNo);
        if(favoriteRepository.existsById(favoritesId)){
            throw new IllegalArgumentException("This store is already in favorites");
        }

        Favorites favorite = new Favorites(favoritesId, user, store);
        favoriteRepository.save(favorite);
    }

    public List<FavoriteResponseDto> getFavoritesByUser(Integer userNo) {
        //사용자 No로 즐겨찾기된 Store 정보를 조회.
        List<Integer> storeNoList = favoriteRepository.findStoreNoListByUser(userNo);

        //Store 정보를 기반으로 FavoriteResponseDto 리스트 생성
        return storeNoList.stream().map(storeNo -> {
            Store store = storeRepository.findById(storeNo).orElseThrow(() -> new IllegalArgumentException(("Store not found with No:")+storeNo));
            return new FavoriteResponseDto(store.getName(), store.getAddress());
        }).collect(Collectors.toList());
    }

    @Transactional
    public void deleteFavorite(Integer userNo, Integer storeNo) {
        favoriteRepository.deleteByUserNoAndStoreNo(userNo, storeNo);
    }
}

