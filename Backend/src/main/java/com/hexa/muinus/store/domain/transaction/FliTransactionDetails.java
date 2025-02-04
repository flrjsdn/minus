package com.hexa.muinus.store.domain.transaction;

import com.hexa.muinus.store.domain.item.FliItem;
import com.hexa.muinus.store.dto.kiosk.PaymentRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "fli_transaction_details",
        uniqueConstraints = {@UniqueConstraint(name = "unique_fli_transaction_details", columnNames = {"transaction_id", "fli_item_id"})}
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FliTransactionDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    private int detailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", referencedColumnName = "transaction_id", nullable = false)
    private Transactions transaction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fli_item_id", referencedColumnName = "fli_item_id", nullable = false)
    private FliItem fliItem;

    @Column(name = "unit_price", nullable = false)
    private int unitPrice;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "sub_total", nullable = false)
    private int subTotal;

    public static FliTransactionDetails create(Transactions transaction, FliItem fliItem, PaymentRequestDTO requestDTO, int index) {
        return FliTransactionDetails.builder()
                .transaction(transaction)
                .fliItem(fliItem)
                .unitPrice(requestDTO.getFliItemsForPayment().get(index).getPrice())
                .quantity(requestDTO.getFliItemsForPayment().get(index).getQuantity())
                .subTotal(requestDTO.getFliItemsForPayment().get(index).getSubtotal())
                .build();
    }
}
