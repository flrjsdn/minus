package com.hexa.muinus.common.exception;

import jakarta.validation.constraints.NotNull;

public class StoreNotFoundException extends MuinusException {
    public StoreNotFoundException(@NotNull Integer storeNo) {
        super("유효하지 않은 storeNo 입니다. storeNo:" + storeNo, 404);
    }

    public StoreNotFoundException(@NotNull Integer userNo, @NotNull Integer storeNo) {
        super("일치하는 매장이 존재하지 않습니다. userNo:" + userNo + ", storeNo:" + storeNo, 400);
    }
}