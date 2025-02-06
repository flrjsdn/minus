package com.hexa.muinus.common.exception.api.user;

import com.hexa.muinus.common.exception.APIErrorCode;
import com.hexa.muinus.common.exception.MuinusException;
import jakarta.validation.constraints.NotNull;

public class UserNotFoundException extends MuinusException {
    public UserNotFoundException(@NotNull Integer userNo) {
        super(APIErrorCode.USER_NOT_FOUND, "userNo: " + userNo);
    }

    public UserNotFoundException(@NotNull String userEmail) {
        super(APIErrorCode.USER_NOT_FOUND, "userEmail: " + userEmail);
    }

    public UserNotFoundException() {
        super(APIErrorCode.USER_NOT_FOUND);
    }
}