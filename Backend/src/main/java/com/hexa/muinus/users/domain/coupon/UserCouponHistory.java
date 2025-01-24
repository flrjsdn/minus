package com.hexa.muinus.users.domain.coupon;

import com.hexa.muinus.store.domain.coupon.CouponHistory;
import com.hexa.muinus.users.domain.user.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_coupon_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCouponHistory {

    @EmbeddedId
    private UserCouponHistoryId id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "store_no", referencedColumnName = "store_no", insertable = false, updatable = false),
            @JoinColumn(name = "coupon_id", referencedColumnName = "coupon_id", insertable = false, updatable = false)
    })
    private CouponHistory couponHistory;

    @ManyToOne
    @JoinColumn(name = "user_no", referencedColumnName = "user_no", insertable = false, updatable = false)
    private Users user;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "used_at")
    private LocalDateTime usedAt;
}
