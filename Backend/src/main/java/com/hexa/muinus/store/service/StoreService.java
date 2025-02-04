package com.hexa.muinus.store.service;

import com.hexa.muinus.common.enums.YesNo;
import com.hexa.muinus.common.exception.StoreLocationDuplicateException;
import com.hexa.muinus.common.exception.StoreNotFoundException;
import com.hexa.muinus.common.exception.UserNotFoundException;
import com.hexa.muinus.store.domain.information.respository.AnnouncementRepository;
import com.hexa.muinus.store.domain.item.repository.FliItemRepository;
import com.hexa.muinus.store.domain.item.repository.StoreItemRepository;
import com.hexa.muinus.store.domain.store.Store;
import com.hexa.muinus.store.domain.store.repository.StoreRepository;
import com.hexa.muinus.store.dto.*;
import com.hexa.muinus.users.domain.user.Users;
import com.hexa.muinus.users.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreService {
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final AnnouncementRepository announcementRepository;
    private final StoreItemRepository storeItemRepository;
    private final FliItemRepository fliItemRepository;


    /**
     * 매장 등록
     * @param storeRegisterDTO
     */
    public void registerStore(StoreRegisterDTO storeRegisterDTO) {
        log.info("Starting store registration for DTO: {}", storeRegisterDTO);

        // 사용자 유효성 검사
        Users user = userRepository.findById(storeRegisterDTO.getUserNo())
                .orElseThrow(() -> new UserNotFoundException(storeRegisterDTO.getUserNo()));


        // 주소 중복 확인
        if ((storeRepository.findByLocationXAndLocationY(storeRegisterDTO.getLocationX(), storeRegisterDTO.getLocationY())).isPresent()) {
            throw new StoreLocationDuplicateException(storeRegisterDTO.getLocationX(), storeRegisterDTO.getLocationY());
        }

        Store store = storeRegisterDTO.toEntity(user);
        log.debug("Converted StoreRegistDTO to Store entity: {}", store);

        save(store);
    }

    /**
     * Insert Store
     * 
     * @param store
     * @return storeNo 추가된 Store 객체
     */
    private Store save(Store store) {
        Store savedStore = storeRepository.save(store);
        log.info("Store saved successfully with ID: {}", savedStore.getStoreNo());
        return savedStore;
    }

    /**
     * 매장 삭제
     * @param storeNo
     */
    @Transactional
    public void closeStore(int storeNo) {
        log.info("Closing store with No: {}", storeNo);
        Store store = storeRepository.findById(storeNo)
                .orElseThrow(() -> new StoreNotFoundException(storeNo));

        delete(store);
    }

    /**
     * Delete Store
     * @param store
     */
    private void delete(Store store) {
        storeRepository.delete(store);
        log.info("Store deleted successfully with No: {}", store.getStoreNo());
    }

//    /**
//     * 매장 폐업 - row 삭제 하지 않고 deleted 컬럼 사용 시
//     * - 다른 테이블 FK 무결성 및 집계 고려하여 close : update로 사용
//     * - Entity 영속성 사용해서 Transaction 종료 시 Update 되도록 작성
//     *
//     * @param storeNo
//     */
//    @Transactional
//    public void closeStore(int storeNo) {
//        log.info("Closing store with No: {}", storeNo);
//        Store store = storeRepository.findById(storeNo)
//            .orElseThrow(() -> new StoreNotFoundException(storeNo));
//        //store.setDeleted("Y");
//    }


    /**
     * 매장 정보 수정
     * @param storeModifyDTO
     */
    @Transactional
    public void modifyStore(StoreModifyDTO storeModifyDTO) {
        log.info("Modifying store with No: {}", storeModifyDTO.getStoreNo());

        // 사용자 조회
        Users user = userRepository.findById(storeModifyDTO.getUserNo())
                .orElseThrow(() -> new UserNotFoundException(storeModifyDTO.getUserNo()));

        // 사용자 - 매장 조회
        Store store = storeRepository.findByUserAndStoreNo(user, storeModifyDTO.getStoreNo())
                .orElseThrow(() -> new StoreNotFoundException(storeModifyDTO.getUserNo(), storeModifyDTO.getStoreNo()));

        storeModifyDTO.updateEntity(store);
    }

    /**
     * 제품으로 매장 조회 (단순 전체 조회 - 정렬되어 있지 않음)
     * @param itemId
     * @return
     */
    @Transactional(readOnly = true)
    public List<StoreSearchDTO> searchStore(int itemId) {
        log.info("Searching store with itemId: {}", itemId);

        List<StoreSearchDTO> stores = storeRepository.findStoresByItemId(itemId);

        if (stores.isEmpty()) {
            log.warn("No stores found for itemId: {}", itemId);
// 할인가 십의자리 까지 반올림
//        } else {
//            stores.forEach(store -> {
//                int roundedPrice = (int) (Math.round(store.getDiscountPrice() / 10.0) * 10);
//                store.setDiscountPrice(roundedPrice);
//            });
        }

        return stores;
    }

    /**
     * 매장 상세 정보 조회
     * @param storeNo
     * @return
     */
    @Transactional(readOnly = true)
    public StoreDetailDTO getStoreDetail(int storeNo) {
        log.info("Getting store detail with storeNo: {}", storeNo);

        // 매장 조회
        StoreDTO store = storeRepository.findStoreDTOById(storeNo)
                .orElseThrow(() -> new StoreNotFoundException(storeNo));

        // 공지사항 조회
        List<AnnouncementDTO> announcements = announcementRepository.findAnnouncementsByStore(storeNo);

        // 판매 제품 조회
        List<StoreItemDTO> storeItems = storeItemRepository.findStoreItemsByStore(storeNo);

        // 플리마켓 허용 시 플리마켓 제품 조회
        List<FliItemDTO> fliItems = store.getFlimarketYn() == YesNo.Y
                ? fliItemRepository.findSellingFliItemsByStore(storeNo)
                : new ArrayList<>();

        return new StoreDetailDTO(store, announcements, storeItems, fliItems);
    }

    @Transactional(readOnly = true)
    public Store findStoreByStoreNo(int storeNo) {
        return storeRepository.findById(storeNo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 매장입니다."));
    }
}
