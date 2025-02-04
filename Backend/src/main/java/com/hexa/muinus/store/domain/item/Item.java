package com.hexa.muinus.store.domain.item;

import com.hexa.muinus.common.enums.YesNo;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Integer itemId;

    @Column(name = "barcode", nullable = false, unique = true, length = 50)
    private String barcode;

    @Column(name = "item_name", nullable = false, length = 200)
    private String itemName;

    @Column(nullable = false, length = 200)
    private String brand;

    @Column(nullable = false)
    private Integer calories = 0;

    @Column(nullable = false)
    private Integer protein = 0;

    @Column(nullable = false)
    private Integer fat = 0;

    @Column(nullable = false)
    private Integer carbohydrate = 0;

    @Column(nullable = false)
    private Integer sugars = 0;

    @Column(nullable = false)
    private Integer weight;

    @Column(name = "item_image_url", length = 255)
    private String itemImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "deleted", nullable = false, columnDefinition = "ENUM('Y', 'N')")
    private YesNo deleted = YesNo.N;

}
