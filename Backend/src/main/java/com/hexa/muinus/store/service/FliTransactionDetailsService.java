package com.hexa.muinus.store.service;

import com.hexa.muinus.store.domain.transaction.FliTransactionDetails;
import com.hexa.muinus.store.domain.transaction.repository.FliTransactionDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FliTransactionDetailsService {

    private final FliTransactionDetailsRepository fliTransactionDetailsRepository;

    public FliTransactionDetails save(FliTransactionDetails fliTransactionDetails) {
        return fliTransactionDetailsRepository.save(fliTransactionDetails);
    }
}
