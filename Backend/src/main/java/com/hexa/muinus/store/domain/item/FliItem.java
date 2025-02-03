package com.hexa.muinus.store.domain.item;

import com.hexa.muinus.store.domain.store.Store;
import com.hexa.muinus.users.domain.user.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "fli_item")
public class FliItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fli_item_id")
    private Integer fliItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_no", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", nullable = false)
    private Users users;

    @Column(name = "fli_item_name", nullable = false, length = 200)
    private String fliItemName;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity = 1;

    @Column(name = "section_id", nullable = false, columnDefinition = "TINYINT UNSIGNED")
    private Integer sectionId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('PENDING', 'APPROVED', 'REJECTED', 'SELLING', 'SOLD')")
    private FliItemStatus status = FliItemStatus.PENDING;

    @Column(name = "application_date", nullable = false, updatable = false, insertable = false)
    private LocalDateTime applicationDate;

    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate;

    public enum FliItemStatus {
        PENDING,
        APPROVED,
        REJECTED,
        SELLING,
        SOLD
    }

}
