package com.jee.foodstore.models;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Reference;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
@Entity("cart_products")
public class CartProduct {
    @Id
    private ObjectId id;
    @Reference
    private Cart cart;
    @Reference
    private Product product;
    private int quantity;
}
