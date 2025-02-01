package com.hexa.muinus.store.controller;

import com.hexa.muinus.store.dto.CouponRequestDto;
import com.hexa.muinus.store.service.CouponService;
import com.hexa.muinus.users.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping("api/coupon")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    // 쿠폰 생성
    @PostMapping("/create")
    public ResponseEntity<?> createCoupon(@Valid @RequestBody CouponRequestDto couponRequestDto){
        couponService.createCoupon(couponRequestDto);
        return ResponseEntity.ok("쿠폰 생성이 완료되었습니다.");
    }

    // 쿠폰 수령
    @PostMapping("/receive")
    public ResponseEntity<?> receiveCoupon(@Valid @RequestBody ReceiveCouponRequestDto receiveCouponRequestDto){
        couponService.receiveCoupon(receiveCouponRequestDto);
        return ResponseEntity.ok("쿠폰 수령이 완료되었습니다.");
    }

    // 보유 쿠폰 전체 조회
    @GetMapping("/receive/user/{userNo}")
    public ResponseEntity<List<ReceiveCouponResponseDto>> getUserCoupons(@PathVariable Integer userNo){
        List<ReceiveCouponResponseDto> userCoupons = couponService.getUserCoupons(userNo);
        return ResponseEntity.ok(userCoupons);
    }

    // 쿠폰 사용
    @PostMapping("/use")
    public ResponseEntity<UseCouponResponseDto> useCoupon(@Valid @RequestBody UseCouponRequestDto useCouponRequestDto){
        UseCouponResponseDto responseDto = couponService.useCoupon(useCouponRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseDto);
    }

    // 할인율 적용
    @PostMapping("/discount")
    public ResponseEntity<ApplyDiscountResponseDto> applyDiscount(@Valid @RequestBody ApplyDisCountRequestDto applyDisCountRequestDto){
        ApplyDiscountResponseDto responseDto = couponService.applyDiscount(applyDisCountRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
