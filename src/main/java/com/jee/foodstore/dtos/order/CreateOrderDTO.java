package com.jee.foodstore.dtos.order;

import com.jee.foodstore.dtos.cart.CartProductDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;

@Data
public class CreateOrderDTO {
    private BigDecimal totalAmount;
    private String status;
    private ArrayList<CartProductDTO> products;
}
