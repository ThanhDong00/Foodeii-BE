package com.jee.foodstore.models;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Reference;
import lombok.Data;
import org.bson.types.ObjectId;

import java.math.BigDecimal;

@Data
@Entity("order_products")
public class OrderProduct {
    @Id
    private ObjectId id;
    @Reference
    private Order order;
    @Reference
    private Product product;
    private int quantity;
    private BigDecimal price;
}
