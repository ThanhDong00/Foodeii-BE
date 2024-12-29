package com.jee.foodstore.models;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Reference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@Entity("order_products")
@EqualsAndHashCode(callSuper = true)
public class OrderProduct extends BaseEntity {
    @Reference
    private Order order;
    @Reference
    private Product product;
    private int quantity;
    private BigDecimal price;
}
