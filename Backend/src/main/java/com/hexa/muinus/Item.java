package com.hexa.muinus;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long itemId;

    @Column
    private String barcode;

    @Column
    private String itemName;

    @Column
    private String brand;

    @Column
    private Integer calories;

    @Column
    private Integer protein;

    @Column
    private Integer fat;

    @Column
    private Integer carbohydrate;

    @Column
    private Integer sugars;

    @Column
    private Integer weight;

    @Column
    private String itemImageUrl;

    @Builder
    public Item(Long itemId, String barcode, String itemName, String brand, Integer calories, Integer protein, Integer fat, Integer carbohydrate, Integer sugars, Integer weight, String itemImageUrl) {
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
