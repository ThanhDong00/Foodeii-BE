package com.jee.foodstore.services.interfaces;


import com.jee.foodstore.dtos.cart.AddCartProductDTO;
import com.jee.foodstore.dtos.cart.CartDTO;
import com.jee.foodstore.dtos.cart.CartProductDTO;
import com.jee.foodstore.models.Cart;

import java.util.List;

public interface ICartService {
//    Cart getCart();
//    Cart addProduct(AddCartProductDTO addCartProductDTO);
//    Cart updateQuantity(String productId, int quantity);
//    Cart removeProduct(String productId);
//    void clearCart();

    CartDTO getCart();
    CartDTO addProduct(AddCartProductDTO product);
    CartDTO updateQuantity(String productId, int quantity);
    CartDTO removeProduct(String productId);
    void clearCart();
}
