package com.jee.foodstore.models;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Reference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@Entity("products")
@EqualsAndHashCode(callSuper = true)
public class Product extends BaseEntity {
    private String name;
    private String description;
    private BigDecimal price;
    @Reference
    private Category category;
    private int stockQuantity;
    private String imageUrl;
}
