package com.hexa.muinus.users.domain.coupon.repository;

import com.hexa.muinus.users.domain.coupon.UserCouponHistory;
import com.hexa.muinus.users.domain.coupon.UserCouponHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCouponHistoryRepository extends JpaRepository<UserCouponHistory, UserCouponHistoryId> {


}
