package com.hexa.muinus.common.exception.api.coupon;

import com.hexa.muinus.common.exception.APIErrorCode;
import com.hexa.muinus.common.exception.MuinusException;
import jakarta.validation.constraints.NotNull;

public class CouponNotFoundException extends MuinusException {
    public CouponNotFoundException() {
        super(APIErrorCode.COUPON_NOT_FOUND);
    }
    public CouponNotFoundException(@NotNull Integer couponId) {
        super(APIErrorCode.COUPON_NOT_FOUND, String.format("couponId: %d", couponId));
    }
}
