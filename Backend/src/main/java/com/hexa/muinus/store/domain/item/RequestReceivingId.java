package com.hexa.muinus.store.domain.item;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestReceivingId implements Serializable {
    private Integer storeNo;
    private Integer itemId;
    private Integer userNo;
}

