package com.hexa.muinus.store.dto.kiosk;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentResponseDTO {

    private String receiptCode;
}
