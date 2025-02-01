package com.hexa.muinus.store.domain.item;

import com.hexa.muinus.store.domain.store.Store;
import com.hexa.muinus.users.domain.user.Users;
import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Entity
@Table(name = "request_receiving")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestReceiving {

    @EmbeddedId
    private RequestReceivingId id;

    @MapsId("storeNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_no", nullable = false)
    private Store store;

    @MapsId("productId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Item item;

    @MapsId("userNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", nullable = false)
    private Users user;

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;
}

