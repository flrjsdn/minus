package com.hexa.muinus.store.domain.transaction.repository;

import com.hexa.muinus.store.domain.transaction.FliTransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FliTransactionDetailsRepository extends JpaRepository<FliTransactionDetails, Long> {
}
