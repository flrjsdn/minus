package com.hexa.muinus.common.exception.api.user;

import com.hexa.muinus.common.exception.APIErrorCode;
import com.hexa.muinus.common.exception.MuinusException;
import jakarta.validation.constraints.NotNull;

public class UserEmailDuplicateException extends MuinusException {
    public UserEmailDuplicateException(@NotNull String userEmail) {
        super(APIErrorCode.USER_EMAIL_DUPLICATE, "userEmail: " + userEmail);
    }
}