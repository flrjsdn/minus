package com.hexa.muinus;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long userNo;

    @Column
    private String userName;

    @Column
    private String email;

    @Column
    private String telephone;

    @Column
    private UserType userType = UserType.U;

    @Column
    private Integer point;

    public enum UserType {
        A, U
    }

    @Builder
    public Users(Long userNo, String userName, String email, String telephone, UserType userType) {
        this.userNo = userNo;
        this.userName = userName;
        this.email = email;
        this.telephone = telephone;
        this.userType = userType;
    }
}