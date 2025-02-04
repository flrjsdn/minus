package com.hexa.muinus.store.domain.transaction.repository;

import com.hexa.muinus.store.domain.transaction.GuestTransactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestTransactionsRepository extends JpaRepository<GuestTransactions, Long> {
}
