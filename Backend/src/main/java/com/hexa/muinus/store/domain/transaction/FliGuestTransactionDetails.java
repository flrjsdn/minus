package com.hexa.muinus.store.domain.transaction;

import com.hexa.muinus.store.domain.item.FliItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "fli_guest_transaction_details",
        uniqueConstraints = {@UniqueConstraint(name = "unique_fli_guest_transaction_details", columnNames = {"transaction_id", "fli_item_id"})}
)
public class FliGuestTransactionDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    private Integer detailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", nullable = false)
    private GuestTransactions transactions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fli_item_id", nullable = false)
    private FliItem fliItem;

    @Column(name = "unit_price", nullable = false)
    private int unitPrice;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "sub_total", nullable = false)
    private int subTotal;

}

