package com.hexa.muinus.users.domain.favorite;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FavoritesId implements Serializable {

    private Integer userNo;
    private Integer storeNo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoritesId that = (FavoritesId) o;
        return Objects.equals(userNo, that.userNo) && Objects.equals(storeNo, that.storeNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userNo, storeNo);
    }
}
