package com.hexa.muinus.store.domain.transaction.repository;

import com.hexa.muinus.store.domain.transaction.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionsRepository extends JpaRepository<Transactions, Long> {
}
