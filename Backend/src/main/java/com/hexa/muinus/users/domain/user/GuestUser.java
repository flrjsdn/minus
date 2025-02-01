package com.hexa.muinus.users.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@Table(name = "guest_user")
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class GuestUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer guestNo;

    @Column(nullable = false, length = 200)
    private String guestName;

}

