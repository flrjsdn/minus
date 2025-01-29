package com.hexa.muinus.store.domain.coupon.controller;

import com.hexa.muinus.store.domain.coupon.dto.CouponRequestDto;
import com.hexa.muinus.store.domain.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/coupon")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping("/create")
    public ResponseEntity<?> createCoupon(@RequestBody CouponRequestDto couponRequestDto){
        couponService.createCoupon();
    }
}
