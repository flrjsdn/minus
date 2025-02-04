package com.hexa.muinus.users.domain.user;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "guest_user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guest_no")
    private Integer guestNo;

    @Column(name = "guest_name", nullable = false, length = 200)
    private String guestName;

}
