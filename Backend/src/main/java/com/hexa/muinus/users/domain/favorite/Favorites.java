package com.hexa.muinus.users.domain.favorite;

import com.hexa.muinus.store.domain.store.Store;
import com.hexa.muinus.users.domain.user.Users;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "favorites")
public class Favorites {

    @EmbeddedId
    private FavoritesId id;

    @MapsId("user")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", nullable = false)
    private Users user;

    @MapsId("store")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_no", nullable = false)
    private Store store;

}
