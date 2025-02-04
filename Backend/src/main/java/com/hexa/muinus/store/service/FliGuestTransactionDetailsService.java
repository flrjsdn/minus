package com.hexa.muinus.store.service;

import com.hexa.muinus.store.domain.transaction.FliGuestTransactionDetails;
import com.hexa.muinus.store.domain.transaction.repository.FliGuestTransactionDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FliGuestTransactionDetailsService {

    private final FliGuestTransactionDetailsRepository fliGuestTransactionDetailsRepository;

    public FliGuestTransactionDetails save(FliGuestTransactionDetails fliGuestTransactionDetails) {
        return fliGuestTransactionDetailsRepository.save(fliGuestTransactionDetails);
    }
}
