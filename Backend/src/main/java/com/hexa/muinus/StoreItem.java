package com.hexa.muinus;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class StoreItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long storePrdtId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_no", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column
    private Integer quantity;

    @Column
    private Integer salePrice;

    @Column
    private Integer discountRate = 0;

    @Builder
    public StoreItem(Long storePrdtId, Store store, Item item, Integer quantity, Integer salePrice, Integer discountRate) {
        this.storePrdtId = storePrdtId;
        this.store = store;
        this.item = item;
        this.quantity = quantity;
        this.salePrice = salePrice;
        this.discountRate = discountRate;
    }
}