package com.hexa.muinus.common.exception.api.store;

import com.hexa.muinus.common.exception.APIErrorCode;
import com.hexa.muinus.common.exception.MuinusException;

public class BoardForbiddenException extends MuinusException {
    public BoardForbiddenException() {
        super(APIErrorCode.BOARD_FORBIDDEN);
    }
    public BoardForbiddenException(String userEmail, Integer boardId) {
        super(APIErrorCode.BOARD_FORBIDDEN, String.format("userEmail: %s, boardId: %d", userEmail, boardId));
    }
}
