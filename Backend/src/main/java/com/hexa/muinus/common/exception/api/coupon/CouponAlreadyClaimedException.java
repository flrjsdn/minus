package com.hexa.muinus.common.exception.api.coupon;

import com.hexa.muinus.common.exception.APIErrorCode;
import com.hexa.muinus.common.exception.MuinusException;

public class CouponAlreadyClaimedException extends MuinusException {
    public CouponAlreadyClaimedException() {
        super(APIErrorCode.COUPON_ALREADY_CLAIMED);
    }
    public CouponAlreadyClaimedException(int storeNo, int couponId) {
        super(APIErrorCode.COUPON_ALREADY_CLAIMED, String.format("storeNo: %d, couponId %d", storeNo, couponId));
    }
}