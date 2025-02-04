package com.hexa.muinus.store.service;

import com.hexa.muinus.store.domain.transaction.Transactions;
import com.hexa.muinus.store.domain.transaction.repository.TransactionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionsService {

    private final TransactionsRepository transactionsRepository;

    @Transactional
    public Transactions save(Transactions transactions) {
        return transactionsRepository.save(transactions);
    }
}
