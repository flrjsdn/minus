package com.hexa.muinus.store.domain.transaction.repository;

import com.hexa.muinus.store.domain.transaction.GuestTransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestTransactionDetailsRepository extends JpaRepository<GuestTransactionDetails, Long> {
}
