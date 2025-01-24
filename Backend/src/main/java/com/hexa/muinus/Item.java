package com.hexa.muinus;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "item")
@Getter
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Integer itemId;

    @Column(nullable = false, length = 50)
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

    @Column(name = "item_image_url")
    private String itemImageUrl;

    @Builder
    public Item(Integer itemId, String barcode, String itemName, String brand, Integer calories, Integer protein, Integer fat, Integer carbohydrate, Integer sugars, Integer weight, String itemImageUrl) {
        this.itemId = itemId;
        this.barcode = barcode;
        this.itemName = itemName;
        this.brand = brand;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrate = carbohydrate;
        this.sugars = sugars;
        this.weight = weight;
        this.itemImageUrl = itemImageUrl;
    }
}
