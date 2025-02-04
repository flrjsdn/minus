package com.hexa.muinus.store.controller;

import com.hexa.muinus.store.dto.StoreDetailDTO;
import com.hexa.muinus.store.dto.StoreModifyDTO;
import com.hexa.muinus.store.dto.StoreRegisterDTO;
import com.hexa.muinus.store.dto.StoreSearchDTO;
import com.hexa.muinus.store.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/store")
@RequiredArgsConstructor
@Slf4j
public class StoreController {

    private final StoreService storeService;

    /**
     * 매장 등록
     *
     * @param storeRegisterDTO
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<Void> registerStore(@Valid @RequestBody StoreRegisterDTO storeRegisterDTO){
        log.info("StoreController > registerStore: {}", storeRegisterDTO);

        storeService.registerStore(storeRegisterDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * 매장 비활성화 - 진짜 row 삭제
     * @param storeNo
     * @return
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Void> closeStore(@RequestParam("storeNo") int storeNo){
        log.info("StoreController > closeStore: {}", storeNo);
        storeService.closeStore(storeNo);
        return ResponseEntity.ok().build();
    }

    /**
     * 매장 비활성화 - 운영팀에서 , 비활성화 컬럼 사용 시
     *
     * @param storeNo
     * @return
     */
//    @PutMapping("/delete")
//    public ResponseEntity<Void> closeStore(@RequestParam("storeNo") int storeNo){
//        log.info("StoreController > closeStore - storeNo: {}", storeNo);
//        storeService.closeStore(storeNo);
//        return ResponseEntity.ok().build();
//    }

    /**
     * 매장 정보 수정
     * @param storeModifyDTO
     * @return
     */
    @PutMapping("/update")
    public ResponseEntity<Void> modifyStore(@Valid @RequestBody StoreModifyDTO storeModifyDTO){
        log.info("StoreController > modifyStore: {}", storeModifyDTO);
        storeService.modifyStore(storeModifyDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * 제품으로 매장리스트 조회
     * @param itemId
     * @return List<StoreSearchDTO>
     * - 해당 제품이 등록이 된 매장 정보s 반환
     */
    @GetMapping("/list")
    public ResponseEntity<List<StoreSearchDTO>> searchStores(@RequestParam("itemId") int itemId){
        log.info("StoreController > searchStores: {}", itemId);
        List<StoreSearchDTO> stores = storeService.searchStore(itemId);
        return ResponseEntity.ok().body(stores);
    }

    @GetMapping("/detail")
    public ResponseEntity<StoreDetailDTO> getStoreDetail(@RequestParam("storeNo") int storeNo){
        log.info("StoreController > getStoreDetail: {}", storeNo);
        StoreDetailDTO store = storeService.getStoreDetail(storeNo);
        return ResponseEntity.ok().body(store);
    }

}
