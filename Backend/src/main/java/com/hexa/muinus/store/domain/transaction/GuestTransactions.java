package com.hexa.muinus.store.domain.transaction;

import com.hexa.muinus.store.domain.store.Store;
import com.hexa.muinus.users.domain.user.GuestUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "guest_transactions")
public class GuestTransactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionId;

    @ManyToOne(fetch = FetchType.LAZY) // ManyToOne 관계 설정
    @JoinColumn(name = "store_no", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY) // ManyToOne 관계 설정
    @JoinColumn(name = "guest_no", nullable = false)
    private GuestUser guest;

    @Column(nullable = false, length = 50)
    private String receiptCode;

    @Column(nullable = false)
    private int totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "ENUM('SUCCESS', 'FAILED', 'REFUNDED') DEFAULT 'SUCCESS'")
    private Status status;

    @Column(name = "created_at", insertable = false, nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    public enum Status {
        SUCCESS,
        FAILED,
        REFUNDED,
    }
}
