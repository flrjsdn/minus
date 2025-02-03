package com.hexa.muinus.users.domain.favorite;

import com.hexa.muinus.store.domain.store.Store;
import com.hexa.muinus.users.domain.user.Users;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "favorites")
public class Favorites {

    @EmbeddedId
    private FavoritesId id;

    @MapsId("userNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", referencedColumnName = "user_no", nullable = false, insertable = false, updatable = false)
    private Users user;

    @MapsId("storeNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_no", referencedColumnName = "store_no", nullable = false, insertable = false, updatable = false)
    private Store store;

}
