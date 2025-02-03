package com.hexa.muinus.store.domain.store;

import com.hexa.muinus.common.enums.YesNo;
import com.hexa.muinus.users.domain.user.Users;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "store",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_location", columnNames = {"location_x", "location_y"})
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_no")
    private Integer storeNo;

    @ManyToOne
    @JoinColumn(name = "user_no", nullable = false)
    private Users user;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "location_x", nullable = false, precision = 10, scale = 7)
    private BigDecimal locationX;

    @Column(name = "location_y", nullable = false, precision = 10, scale = 7)
    private BigDecimal locationY;

    @Column(name = "address", nullable = false, length = 255, unique = true)
    private String address;

    @Column(name = "store_image_url", length = 255)
    private String storeImageUrl;

    @Column(name = "registration_no", nullable = false, unique = true, length = 255)
    private String registrationNo;

    @Column(length = 20)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "flimarket_yn", nullable = false, columnDefinition = "ENUM('Y', 'N')")
    private YesNo flimarketYn = YesNo.N;

    @Column(name = "flimarket_image_url", length = 255)
    private String flimarketImageUrl;

    @Column(name = "flimarket_section_cnt", nullable = false, columnDefinition = "TINYINT UNSIGNED")
    private Integer flimarketSectionCnt = 0;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "deleted", nullable = false, columnDefinition = "ENUM('Y', 'N')")
    private YesNo deleted = YesNo.N;

}
