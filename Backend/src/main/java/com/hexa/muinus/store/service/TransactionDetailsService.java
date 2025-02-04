package com.hexa.muinus.store.service;

import com.hexa.muinus.store.domain.transaction.TransactionDetails;
import com.hexa.muinus.store.domain.transaction.repository.TransactionDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionDetailsService {

    private final TransactionDetailsRepository transactionDetailsRepository;

    @Transactional
    public TransactionDetails save(TransactionDetails transactionDetails) {
        return transactionDetailsRepository.save(transactionDetails);
    }
}
