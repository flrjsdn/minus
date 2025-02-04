package com.hexa.muinus.store.service;

import com.hexa.muinus.store.domain.transaction.GuestTransactions;
import com.hexa.muinus.store.domain.transaction.repository.GuestTransactionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GuestTransactionsService {

    private final GuestTransactionsRepository guestTransactionsRepository;

    @Transactional
    public GuestTransactions save(GuestTransactions guestTransactions) {
        return guestTransactionsRepository.save(guestTransactions);
    }
}
