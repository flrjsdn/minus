package com.hexa.muinus.users.domain.user;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "fli_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FliUser {

    @Id
    @Column(name = "user_no")
    private Integer userNo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", referencedColumnName = "user_no", insertable = false, updatable = false)
    @MapsId
    private Users user;

    @Column(nullable = false, length = 50)
    private String bank;

    @Column(name = "account_number", nullable = false, length = 50)
    private String accountNumber;

    @Column(name = "account_name", nullable = false, length = 20)
    private String accountName;
}

