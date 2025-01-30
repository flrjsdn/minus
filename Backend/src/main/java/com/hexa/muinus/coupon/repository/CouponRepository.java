package com.hexa.muinus.store.domain.coupon.repository;

import com.hexa.muinus.store.domain.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {
}
