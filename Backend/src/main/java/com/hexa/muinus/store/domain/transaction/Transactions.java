package com.hexa.muinus.store.domain.transaction;

import com.hexa.muinus.store.domain.store.Store;
import com.hexa.muinus.users.domain.user.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private int transactionId;

    @Column(name = "receipt_code", nullable = false, length = 20)
    private String receiptCode;

    @ManyToOne
    @JoinColumn(name = "store_no", referencedColumnName = "store_no", nullable = false)
    private Store store;

    @ManyToOne
    @JoinColumn(name = "user_no", referencedColumnName = "user_no", nullable = false)
    private Users user;

    @Column(name = "total_amount", nullable = false)
    private int totalAmount;

    @Column(name = "status", nullable = false, columnDefinition = "ENUM('SUCCESS', 'FAILED', 'REFUNDED') DEFAULT 'SUCCESS'")
    private String status;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;
}
