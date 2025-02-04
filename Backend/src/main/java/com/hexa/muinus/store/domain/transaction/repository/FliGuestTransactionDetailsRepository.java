package com.hexa.muinus.store.domain.transaction.repository;

import com.hexa.muinus.store.domain.transaction.FliGuestTransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FliGuestTransactionDetailsRepository extends JpaRepository<FliGuestTransactionDetails, Long> {
}
