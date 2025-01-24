package com.hexa.muinus;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "store_item")
@Getter
@NoArgsConstructor
public class StoreItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_prdt_id")
    private Integer storePrdtId;

    @ManyToOne
    @JoinColumn(name = "store_no", nullable = false)
    private Store store;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "sale_price", nullable = false)
    private Integer salePrice;

    @Column(name = "discount_rate", nullable = false)
    private Integer discountRate = 0;

    @Builder

    public StoreItem(Integer storePrdtId, Store store, Item item, Integer quantity, Integer salePrice, Integer discountRate) {
        this.storePrdtId = storePrdtId;
        this.store = store;
        this.item = item;
        this.quantity = quantity;
        this.salePrice = salePrice;
        this.discountRate = discountRate;
    }
}
