package com.hexa.muinus.store.domain.transaction;

import com.hexa.muinus.store.domain.store.Store;
import com.hexa.muinus.users.domain.user.Users;
import com.hexa.muinus.store.domain.transaction.GuestTransactions.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private int transactionId;

    @Column(name = "receipt_code", nullable = false, length = 50)
    private String receiptCode;

    @ManyToOne
    @JoinColumn(name = "store_no", referencedColumnName = "store_no", nullable = false)
    private Store store;

    @ManyToOne
    @JoinColumn(name = "user_no", referencedColumnName = "user_no", nullable = false)
    private Users user;

    @Column(name = "total_amount", nullable = false)
    private int totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "ENUM('SUCCESS', 'FAILED', 'REFUNDED') DEFAULT 'SUCCESS'")
    private Status status;

    @Column(name = "created_at", insertable = false, nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;
}
