package com.hexa.muinus.common.exception.api.coupon;

import com.hexa.muinus.common.exception.APIErrorCode;
import com.hexa.muinus.common.exception.MuinusException;

public class CouponOutOfStock extends MuinusException {
    public CouponOutOfStock() {
        super(APIErrorCode.COUPON_OUT_OF_STOCK);
    }

    public CouponOutOfStock(int storeNo, int couponId) {
        super(APIErrorCode.COUPON_OUT_OF_STOCK, String.format("storeNo: %d, couponId %d", storeNo, couponId));
    }
}