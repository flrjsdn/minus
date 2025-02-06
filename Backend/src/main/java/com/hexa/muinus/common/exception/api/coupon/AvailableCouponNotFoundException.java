package com.hexa.muinus.common.exception.api.coupon;

import com.hexa.muinus.common.exception.APIErrorCode;
import com.hexa.muinus.common.exception.MuinusException;

public class AvailableCouponNotFoundException extends MuinusException {
    public AvailableCouponNotFoundException() {
        super(APIErrorCode.AVAILABLE_COUPON_NOT_FOUND);
    }
}