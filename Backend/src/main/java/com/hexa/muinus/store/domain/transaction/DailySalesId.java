package com.hexa.muinus.store.domain.transaction;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DailySalesId implements Serializable {

    private LocalDate saleDate;
    private Integer storeNo;
    private Integer itemId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DailySalesId that = (DailySalesId) o;
        return Objects.equals(saleDate, that.saleDate) && Objects.equals(storeNo, that.storeNo) && Objects.equals(itemId, that.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(saleDate, storeNo, itemId);
    }


}
