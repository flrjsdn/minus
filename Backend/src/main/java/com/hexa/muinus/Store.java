package com.hexa.muinus;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long storeNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", nullable = false)
    private Users user;

    @Column
    private String name;

    @Column
    private String location;

    @Column
    private String address;

    @Column
    private String storeImageUrl;

    @Column
    private String registrationNo;

    @Column
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column
    private FlimarketYn flimarketYn = FlimarketYn.N;

    @Column
    private String flimarketImageUrl;

    @Column
    private Byte flimarketSectionCnt = 0;

    @Column
    private LocalDateTime createdAt;

    public enum FlimarketYn {
        Y, N
    }

    @Builder
    public Store(Long storeNo, Users user, String name, String location, String address, String storeImageUrl, String registrationNo, String phone, FlimarketYn flimarketYn, String flimarketImageUrl, Byte flimarketSectionCnt, LocalDateTime createdAt) {
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
