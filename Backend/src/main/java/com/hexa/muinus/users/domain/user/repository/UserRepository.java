package com.hexa.muinus.users.domain.user.repository;

import com.hexa.muinus.users.domain.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<Users, Integer> {

    Users findByEmail(String email);

    @Modifying
    @Query(value = "UPDATE users SET refresh_token = :refreshToken WHERE email = :email", nativeQuery = true)
    int updateRefreshTokenByEmail(@Param("email") String email, @Param("refreshToken") String refreshToken);
}
