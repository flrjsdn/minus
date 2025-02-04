package com.hexa.muinus.users.domain.user.repository;

import com.hexa.muinus.users.domain.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Integer> {

    Users findByEmail(String email);

    boolean existsByEmail(String email);
}
