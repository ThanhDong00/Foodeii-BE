package com.jee.foodstore.dtos.cart;

import com.jee.foodstore.models.Product;
import lombok.Data;

@Data
public class CartProductDTO {
    private String id;
    private String cartId;
    private Product product;
    private int quantity;
}