package com.hexa.muinus.coupon.repository;

import com.hexa.muinus.coupon.entity.UserCouponHistory;
import com.hexa.muinus.coupon.entity.UserCouponHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCouponHistoryRepository extends JpaRepository<UserCouponHistory, UserCouponHistoryId> {

    Optional<UserCouponHistory> findById_UserNoAndId_CouponIdAndUsedAtIsNull(Integer userNo, Integer couponId);

    boolean existsById(UserCouponHistoryId id);

    //특정 사용자의 모든 쿠폰을 조회
    List<UserCouponHistory> findByUser_userNo(Integer userNo);

}
