package com.hexa.muinus.users.service;

import com.hexa.muinus.users.domain.user.GuestUser;
import com.hexa.muinus.users.domain.user.repository.GuestUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestUserService {

    private final GuestUserRepository guestUserRepository;

    public void save(GuestUser guestUser) {
        guestUserRepository.save(guestUser);
    }
}
