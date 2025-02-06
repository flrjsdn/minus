package com.hexa.muinus.common.exception.api.user;

import com.hexa.muinus.common.exception.APIErrorCode;
import com.hexa.muinus.common.exception.MuinusException;

public class FavoriteStoreDuplicate extends MuinusException {
    public FavoriteStoreDuplicate(int userNo, int storeNo) {
        super(APIErrorCode.FAVORITE_STORE_DUPLICATE, "userNo=" + userNo + ", storeNo=" + storeNo);

    }

    public FavoriteStoreDuplicate(String userEmail, int storeNo) {
        super(APIErrorCode.FAVORITE_STORE_DUPLICATE, "email=" + userEmail + ", storeNo=" + storeNo);

    }
}
