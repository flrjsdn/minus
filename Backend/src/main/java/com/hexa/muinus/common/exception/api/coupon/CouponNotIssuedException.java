package com.hexa.muinus.common.exception.api.coupon;

import com.hexa.muinus.common.exception.APIErrorCode;
import com.hexa.muinus.common.exception.MuinusException;

public class CouponNotIssuedException extends MuinusException {
    public CouponNotIssuedException() {
        super(APIErrorCode.COUPON_NOT_ISSUED);
    }

    public CouponNotIssuedException(int storeNo, int couponId) {
        super(APIErrorCode.COUPON_NOT_ISSUED, String.format("storeNo: %d, couponId %d", storeNo, couponId));
    }
}
