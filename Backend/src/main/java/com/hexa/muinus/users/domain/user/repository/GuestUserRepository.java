package com.hexa.muinus.users.domain.user.repository;

import com.hexa.muinus.users.domain.user.GuestUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestUserRepository extends JpaRepository<GuestUser, Integer> {
}
