package com.hexa.muinus.store.domain.item;

import com.hexa.muinus.common.enums.YesNo;
import com.hexa.muinus.store.domain.store.Store;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "store_item",
        uniqueConstraints = {@UniqueConstraint(name = "unique_store_item", columnNames = {"store_no", "item_id"})}
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_item_id")
    private Integer storeItemId;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "deleted", nullable = false, columnDefinition = "ENUM('Y', 'N')")
    private YesNo deleted = YesNo.N;
}
