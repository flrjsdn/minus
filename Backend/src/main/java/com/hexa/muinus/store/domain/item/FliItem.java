package com.hexa.muinus.store.domain.item;

import com.hexa.muinus.store.domain.store.Store;
import com.hexa.muinus.users.domain.user.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "fli_item")
public class FliItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fliItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_no", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY) // ManyToOne 관계 설정
    @JoinColumn(name = "user_no", nullable = false)
    private Users users;

    @Column(nullable = false, length = 200)
    private String fliItemName;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int sectionId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('PENDING', 'APPROVED', 'REJECTED', 'SELLING', 'SOLD') DEFAULT 'PENDING'")
    private FliItemStatus status;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime applicationDate;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime expirationDate;

    public enum FliItemStatus {
        PENDING,
        APPROVED,
        REJECTED,
        SELLING,
        SOLD
    }

}
