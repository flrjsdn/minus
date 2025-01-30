package com.hexa.muinus.store.domain.coupon.service;

import com.hexa.muinus.store.domain.coupon.Coupon;
import com.hexa.muinus.store.domain.coupon.CouponHistory;
import com.hexa.muinus.store.domain.coupon.CouponHistoryId;
import com.hexa.muinus.store.domain.coupon.dto.CouponRequestDto;
import com.hexa.muinus.store.domain.coupon.repository.CouponHistoryRepository;
import com.hexa.muinus.store.domain.coupon.repository.CouponRepository;
import com.hexa.muinus.store.domain.store.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponHistoryRepository couponHistoryRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public void createCoupon(CouponRequestDto couponRequestDto) {
        // 가게 이름을 통해 가게 번호 조회
        Integer storeNo = storeRepository.findStoreNoByName(couponRequestDto.getStoreName())
                .orElseThrow(() -> new IllegalArgumentException("해당 가게가 존재하지 않습니다. storeName: "+couponRequestDto.getStoreName()));

        // 쿠폰이 존재하는지 확인
        Coupon coupon = couponRepository.findById(couponRequestDto.getCouponId())
                .orElseThrow(() -> new IllegalArgumentException("해당 쿠폰이 존재하지 않습니다. couponId: "+couponRequestDto.getCouponId()));

        // 복합 키 생성
        CouponHistoryId couponHistoryId = new CouponHistoryId(storeNo, couponRequestDto.getCouponId());

        // 쿠폰 발급 내역 생성
        CouponHistory couponHistory = new CouponHistory(couponHistoryId, couponRequestDto.getCount(), couponRequestDto.getExpirationDate(), LocalDateTime.now());
        couponHistoryRepository.save(couponHistory);
    }
}
