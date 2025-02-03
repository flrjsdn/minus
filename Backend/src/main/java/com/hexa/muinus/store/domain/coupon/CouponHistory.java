package com.hexa.muinus.store.domain.coupon;

import com.hexa.muinus.store.domain.store.Store;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "coupon_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponHistory {

    @EmbeddedId
    private CouponHistoryId id;

    @MapsId("storeNo")
    @ManyToOne
    @JoinColumn(name = "store_no", referencedColumnName = "store_no", insertable = false, updatable = false, nullable = false)
    private Store store;

    @MapsId("couponId")
    @ManyToOne
    @JoinColumn(name = "coupon_id", referencedColumnName = "coupon_id", insertable = false, updatable = false, nullable = false)
    private Coupon coupon;

    @Column(name = "count", nullable = false)
    private int count;

    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    private LocalDateTime createdAt;


}

