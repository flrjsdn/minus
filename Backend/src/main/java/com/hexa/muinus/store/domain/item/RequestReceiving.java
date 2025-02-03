package com.hexa.muinus.store.domain.item;

import com.hexa.muinus.store.domain.store.Store;
import com.hexa.muinus.users.domain.user.Users;
import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Entity
@Table(name = "request_receiving")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestReceiving {

    @EmbeddedId
    private RequestReceivingId id;

    @MapsId("storeNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_no", nullable = false, insertable = false, updatable = false)
    private Store store;

    @MapsId("itemId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false, insertable = false, updatable = false)
    private Item item;

    @MapsId("userNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", nullable = false, insertable = false, updatable = false)
    private Users user;

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private Timestamp createdAt;
}

