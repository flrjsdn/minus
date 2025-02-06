package com.hexa.muinus.common.exception.api.coupon;

import com.hexa.muinus.common.exception.APIErrorCode;
import com.hexa.muinus.common.exception.MuinusException;

import java.time.LocalDateTime;

public class CouponExpiredException extends MuinusException {
    public CouponExpiredException() {
        super(APIErrorCode.COUPON_EXPIRED);
    }
    public CouponExpiredException(int couponId) {
        super(APIErrorCode.COUPON_EXPIRED, String.format("couponId: %d", couponId));
    }

    public CouponExpiredException(LocalDateTime expirationDate) {
        super(APIErrorCode.COUPON_EXPIRED, String.format("유효기한: %s", expirationDate));
    }

    public CouponExpiredException(int couponId, int storeNo) {
        super(APIErrorCode.COUPON_EXPIRED, String.format("couponId: %d, storeNo: %d", couponId, storeNo));
    }

    public CouponExpiredException(int couponId, LocalDateTime expirationDate) {
        super(APIErrorCode.COUPON_EXPIRED, String.format("couponId: %d, 사용일자: %s", couponId, expirationDate));
    }

    public CouponExpiredException(int couponId, int storeNo, LocalDateTime expirationDate) {
        super(APIErrorCode.COUPON_EXPIRED, String.format("couponId: %d, storeNo: %s, 사용일자: %s", storeNo, couponId, expirationDate));
    }
}