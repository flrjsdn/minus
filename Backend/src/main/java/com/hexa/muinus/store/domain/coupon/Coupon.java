package com.hexa.muinus.store.domain.coupon;

import jakarta.persistence.*;
import lombok.*;

@Data
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

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "discount_rate", nullable = false)
    private int discountRate;

    @Column(name = "content", length = 255)
    private String content;
}
