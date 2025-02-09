package com.hexa.muinus.store.dto.item;

import com.hexa.muinus.store.domain.item.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDto {

    private Integer itemId;
    private String barcode;
    private String itemName;
    private String brand;
    private Integer calories;
    private Integer protein;
    private Integer fat;
    private Integer carbohydrate;
    private Integer sugars;
    private Integer weight;
    private String itemImageUrl;

    /**
     * 엔티티를 DTO로 변환하는 정적 메서드
     *
     * @param item 변환할 Item 엔티티
     * @return 변환된 ItemDto
     */
    public static ItemDto fromEntity(Item item) {
        if (item == null) {
            return null;
        }

        return ItemDto.builder()
                .itemId(item.getItemId())
                .barcode(item.getBarcode())
                .itemName(item.getItemName())
                .brand(item.getBrand())
                .calories(item.getCalories())
                .protein(item.getProtein())
                .fat(item.getFat())
                .carbohydrate(item.getCarbohydrate())
                .sugars(item.getSugars())
                .weight(item.getWeight())
                .itemImageUrl(item.getItemImageUrl())
                .build();
    }

    /**
     * DTO를 엔티티로 변환하는 정적 메서드
     *
     * @return 변환된 Item 엔티티
     */
    public Item toEntity() {
        return Item.builder()
                .itemId(this.itemId)
                .barcode(this.barcode)
                .itemName(this.itemName)
                .brand(this.brand)
                .calories(this.calories)
                .protein(this.protein)
                .fat(this.fat)
                .carbohydrate(this.carbohydrate)
                .sugars(this.sugars)
                .weight(this.weight)
                .itemImageUrl(this.itemImageUrl)
                .build();
    }
}


