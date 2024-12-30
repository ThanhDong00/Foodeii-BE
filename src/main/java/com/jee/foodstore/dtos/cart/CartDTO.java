package com.jee.foodstore.dtos.cart;

import com.jee.foodstore.dtos.user.UserDTO;
import com.jee.foodstore.models.CartProduct;
import lombok.Data;

import java.util.List;

@Data
public class CartDTO {
    private String id;
    private UserDTO user;
    private List<CartProductDTO> products;
}
