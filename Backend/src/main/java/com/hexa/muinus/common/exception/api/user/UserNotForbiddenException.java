package com.hexa.muinus.common.exception.api.user;

import com.hexa.muinus.common.exception.APIErrorCode;
import com.hexa.muinus.common.exception.MuinusException;
import jakarta.validation.constraints.NotNull;

public class UserNotForbiddenException extends MuinusException {
    public UserNotForbiddenException(@NotNull Integer userNo) {
        super(APIErrorCode.USER_NOT_FORBIDDEN, "userNo: " + userNo);
    }

    public UserNotForbiddenException(@NotNull String userEmail) {
        super(APIErrorCode.USER_NOT_FORBIDDEN, "userEmail: " + userEmail);
    }
}