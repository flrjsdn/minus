package com.hexa.muinus.store.domain.transaction;

import com.hexa.muinus.store.domain.store.Store;
import com.hexa.muinus.store.domain.item.Item;
import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "daily_sales")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailySales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_no", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Temporal(TemporalType.DATE)
    @Column(name = "sale_date", nullable = false)
    private Date saleDate;

    @Column(name = "total_quantity", nullable = false)
    private int totalQuantity;

    @Column(name = "total_amount", nullable = false)
    private int totalAmount;

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false, insertable = false, updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp updatedAt;
}
