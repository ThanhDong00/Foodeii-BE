package com.jee.foodstore.dtos.cart;

import com.jee.foodstore.models.Product;
import lombok.Data;

@Data
public class AddCartProductDTO {
    private String productId;
    private int quantity;
}
