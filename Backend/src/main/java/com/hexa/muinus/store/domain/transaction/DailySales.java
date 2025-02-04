package com.hexa.muinus.store.domain.transaction;

import com.hexa.muinus.store.domain.store.Store;
import com.hexa.muinus.store.domain.item.Item;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "daily_sales")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailySales {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "saleDate", column = @Column(name = "sale_date", nullable = false))
    })
    private DailySalesId id;

    @MapsId("storeNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_no", referencedColumnName = "store_no", nullable = false, insertable = false, updatable = false)
    private Store store;

    @MapsId("itemId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", referencedColumnName = "item_id", nullable = false, insertable = false, updatable = false)
    private Item item;

    @Column(name = "total_quantity", nullable = false)
    private int totalQuantity = 0;

    @Column(name = "total_amount", nullable = false)
    private int totalAmount = 0;
}
