package com.hexa.muinus.store.domain.transaction;

import com.hexa.muinus.store.domain.item.StoreItem;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "transaction_details",
        uniqueConstraints = {@UniqueConstraint(name = "unique_transaction_details", columnNames = {"transaction_id", "store_item_id"})}
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    private int detailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", referencedColumnName = "transaction_id", nullable = false)
    private Transactions transaction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_item_id", referencedColumnName = "store_item_id", nullable = false)
    private StoreItem storeItem;

    @Column(name = "unit_price", nullable = false)
    private int unitPrice;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "sub_total", nullable = false)
    private int subTotal;

}
