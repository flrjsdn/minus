package com.hexa.muinus.common.exception;

import jakarta.validation.constraints.NotNull;

public class UserNotFoundException extends MuinusException {
    public UserNotFoundException(@NotNull Integer userNo) {
        super("유효하지 않은 사용자입니다. userNo: " + userNo, 404);
    }
}