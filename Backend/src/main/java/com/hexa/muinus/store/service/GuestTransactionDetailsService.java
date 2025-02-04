package com.hexa.muinus.store.service;

import com.hexa.muinus.store.domain.transaction.GuestTransactionDetails;
import com.hexa.muinus.store.domain.transaction.repository.GuestTransactionDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GuestTransactionDetailsService {

    private final GuestTransactionDetailsRepository guestTransactionDetailsRepository;

    @Transactional
    public void save(GuestTransactionDetails guestTransactionDetails) {
        guestTransactionDetailsRepository.save(guestTransactionDetails);
    }
}
