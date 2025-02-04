package com.hexa.muinus.users.domain.user;

import com.hexa.muinus.common.enums.YesNo;
import com.hexa.muinus.users.dto.ConsumerRegisterRequestDto;
import com.hexa.muinus.users.dto.StoreOwnerRegisterRequestDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private Integer userNo;

    @Column(name = "user_name", nullable = false, length = 200)
    private String userName;

    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "telephone", length = 20)
    private String telephone;

    @Column(name = "birth", nullable = false, columnDefinition = "DATE")
    private String birth;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false, columnDefinition = "ENUM('A', 'U')")
    private UserType userType = UserType.U;

    @Column(name = "point", nullable = false)
    private Integer point = 0;

    @Column(name = "refresh_token", length = 255)
    private String refreshToken;

    @Enumerated(EnumType.STRING)
    @Column(name = "deleted", nullable = false, columnDefinition = "ENUM('Y', 'N')")
    private YesNo deleted = YesNo.N;

    public enum UserType {
        A, U
    }

    public static Users createConsumer(ConsumerRegisterRequestDto requestDto) {
        return Users.builder()
                .userName(requestDto.getUserName())
                .email(requestDto.getUserEmail())
                .telephone(requestDto.getUserTelephone())
                .birth(requestDto.getUserBirth())
                .userType(UserType.U)
                .point(requestDto.getUserPoint())
                .deleted(YesNo.N)
                .build();
    }

    public static Users createStoreOwner(StoreOwnerRegisterRequestDto requestDto) {
        return Users.builder()
                .userName(requestDto.getUserName())
                .email(requestDto.getUserEmail())
                .telephone(requestDto.getUserTelephone())
                .birth(requestDto.getUserBirth())
                .userType(Users.UserType.A)
                .point(requestDto.getUserPoint())
                .deleted(YesNo.N)
                .build();
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
