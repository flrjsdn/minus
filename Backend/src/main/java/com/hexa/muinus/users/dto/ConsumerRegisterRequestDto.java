package com.hexa.muinus.users.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConsumerRegisterRequestDto {

    private final String userName;
    private final String userEmail;
    private final String userTelephone;
    private final String userBirth;
    private final String userType;
    private final Integer userPoint;
}
