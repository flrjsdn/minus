package com.hexa.muinus.store.domain;

import com.hexa.muinus.users.domain.user.Users;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "store")
@Getter
@NoArgsConstructor
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_no")
    private Integer storeNo;

    @ManyToOne
    @JoinColumn(name = "user_no", nullable = false)
    private Users user;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, columnDefinition = "POINT")
    private String location; // Use a custom type or a converter for the POINT type.

    @Column(nullable = false, length = 255)
    private String address;

    @Column(name = "store_image_url")
    private String storeImageUrl;

    @Column(name = "registration_no", nullable = false, length = 255)
    private String registrationNo;

    @Column(length = 20)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "flimarket_yn", nullable = false, columnDefinition = "ENUM('Y', 'N')")
    private YesNo flimarketYn = YesNo.N;

    @Column(name = "flimarket_image_url")
    private String flimarketImageUrl;

    @Column(name = "flimarket_section_cnt", nullable = false)
    private Byte flimarketSectionCnt = 0;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    private LocalDateTime createdAt;

    public enum YesNo {
        Y, N
    }

    @Builder
    public Store(Integer storeNo, Users user, String name, String location, String address, String storeImageUrl, String registrationNo, String phone, YesNo flimarketYn, String flimarketImageUrl, Byte flimarketSectionCnt, LocalDateTime createdAt) {
        this.storeNo = storeNo;
        this.user = user;
        this.name = name;
        this.location = location;
        this.address = address;
        this.storeImageUrl = storeImageUrl;
        this.registrationNo = registrationNo;
        this.phone = phone;
        this.flimarketYn = flimarketYn;
        this.flimarketImageUrl = flimarketImageUrl;
        this.flimarketSectionCnt = flimarketSectionCnt;
        this.createdAt = createdAt;
    }
}
