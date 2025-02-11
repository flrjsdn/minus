package com.hexa.muinus.users.domain.preference;

import com.hexa.muinus.store.domain.transaction.DailySalesId;
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
public class PreferenceId implements Serializable {

    private Long userNo;
    private Long itemId;
    private LocalDate updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PreferenceId that = (PreferenceId) o;
        return Objects.equals(userNo, that.userNo) && Objects.equals(itemId, that.itemId) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userNo, itemId, updatedAt);
    }
}
