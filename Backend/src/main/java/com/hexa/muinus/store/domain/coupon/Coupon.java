package com.hexa.muinus.store.domain.coupon;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "coupon")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Integer couponId;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false)
    private int discountRate;

    @Column(nullable = true)
    private String content;
}
