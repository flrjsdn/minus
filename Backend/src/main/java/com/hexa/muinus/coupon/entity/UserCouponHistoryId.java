package com.hexa.muinus.users.domain.coupon;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCouponHistoryId implements Serializable {

    @Column(name = "store_no")
    private int storeNo;

    @Column(name = "coupon_id")
    private int couponId;

    @Column(name = "user_no")
    private int userNo;
}
