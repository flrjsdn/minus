package com.hexa.muinus;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private Integer userNo;

    @Column(name = "user_name", nullable = false, length = 200)
    private String userName;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(length = 20)
    private String telephone;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false, columnDefinition = "ENUM('A', 'U')")
    private UserType userType = UserType.U;

    @Column(nullable = false)
    private Integer point;

    public enum UserType {
        A, U
    }

    @Builder
    public Users(Integer userNo, String userName, String email, String telephone, UserType userType, Integer point) {
        this.userNo = userNo;
        this.userName = userName;
        this.email = email;
        this.telephone = telephone;
        this.userType = userType;
        this.point = point;
    }
}
