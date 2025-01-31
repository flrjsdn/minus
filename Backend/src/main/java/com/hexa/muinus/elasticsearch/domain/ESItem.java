package com.hexa.muinus.elasticsearch.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Document(indexName = "items")
public class ESItem {

    @Id
    private Integer itemId;

    @Field(name = "barcode", type = FieldType.Keyword)
    private String barcode;

    @Field(name = "item_name", type = FieldType.Text)
    private String itemName;

    @Field(name = "brand", type = FieldType.Keyword)
    private String brand;

    @Field(name = "calories", type = FieldType.Integer)
    private Integer calories;

    @Field(name = "protein", type = FieldType.Integer)
    private Integer protein;

    @Field(name = "fat", type = FieldType.Float) // 데이터 타입을 Float으로 변경
    private Float fat;

    @Field(name = "carbohydrate", type = FieldType.Integer)
    private Integer carbohydrate;

    @Field(name = "sugars", type = FieldType.Integer)
    private Integer sugars;

    @Field(name = "weight", type = FieldType.Integer)
    private Integer weight;

    @Field(name = "item_image_url", type = FieldType.Keyword)
    private String itemImageUrl;

    @Field(name = "updated_at", type = FieldType.Date, format = DateFormat.date_optional_time)
    private LocalDateTime updatedAt;

//    @CompletionField(maxInputLength = 100)
//    private Completion suggest;
}
