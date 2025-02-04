package com.hexa.muinus.users.domain.coupon;

import com.hexa.muinus.store.domain.coupon.CouponHistory;
import com.hexa.muinus.users.domain.user.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_coupon_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCouponHistory {

    @EmbeddedId
    private UserCouponHistoryId id;

    @MapsId("couponHistoryId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "store_no", referencedColumnName = "store_no", insertable = false, updatable = false, nullable = false),
            @JoinColumn(name = "coupon_id", referencedColumnName = "coupon_id", insertable = false, updatable = false, nullable = false)
    })
    private CouponHistory couponHistory;

    @MapsId("userNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", referencedColumnName = "user_no", insertable = false, updatable = false, nullable = false)
    private Users user;

    @Column(name = "created_at", nullable = false, updatable = false,  insertable = false)
    private LocalDateTime createdAt;

    @Column(name = "used_at")
    private LocalDateTime usedAt;
}

