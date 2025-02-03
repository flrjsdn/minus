package com.hexa.muinus.common.exception;

import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

public class StoreLocationDuplicateException extends MuinusException {
    public StoreLocationDuplicateException(BigDecimal x, BigDecimal y) {
        super("이미 등록된 위치입니다. locationX:" + x + ", locationY:" + y, 400);
    }
}