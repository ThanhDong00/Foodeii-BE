package com.jee.foodstore.services;


import com.jee.foodstore.dtos.cart.AddCartProductDTO;
import com.jee.foodstore.dtos.cart.CartDTO;
import com.jee.foodstore.dtos.cart.CartProductDTO;
import com.jee.foodstore.models.Cart;
import com.jee.foodstore.models.CartProduct;
import com.jee.foodstore.models.Product;
import com.jee.foodstore.models.User;
import com.jee.foodstore.services.interfaces.ICartService;
import com.jee.foodstore.utils.MongoDBConnect;
import dev.morphia.Datastore;
import dev.morphia.query.FindOptions;
import dev.morphia.query.filters.Filters;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestScoped
public class CartService implements ICartService {
    private final HttpHeaders headers;
    private final Datastore datastore;
    private final UserService userService;

    @Inject
    public CartService(@Context HttpHeaders headers, UserService userService) {
        this.headers = headers;
        this.datastore = MongoDBConnect.getDatastore();
        this.userService = userService;
    }


    @Override
    public CartDTO getCart() {
        String userId = userService.getCurrentUserId();
        Cart cart = findOrCreateCart(userId);
        return mapToDTO(cart);
    }

    @Override
    public CartDTO addProduct(AddCartProductDTO addCartProductDTO) {
        if (addCartProductDTO.getQuantity() <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }

        String userId = userService.getCurrentUserId();
        Cart cart = findOrCreateCart(userId);

        Product product = datastore.find(Product.class)
                .filter(Filters.eq("_id", new ObjectId(addCartProductDTO.getProductId())))
                .first();

        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        CartProduct cartProduct = cart.getCartProducts().stream()
                .filter(cp -> cp.getProduct().getId().toString().equals(addCartProductDTO.getProductId()))
                .findFirst()
                .orElse(null);

        if (cartProduct != null) {
            cartProduct.setQuantity(cartProduct.getQuantity() + addCartProductDTO.getQuantity());
            datastore.save(cartProduct);
        } else {
            cartProduct = new CartProduct();
            cartProduct.setCartId(cart.getId());
            cartProduct.setProduct(product);
            cartProduct.setQuantity(addCartProductDTO.getQuantity());
            datastore.save(cartProduct);
            cart.getCartProducts().add(cartProduct);
        }

        datastore.save(cart);
        return mapToDTO(cart);
    }

    @Override
    public CartDTO updateQuantity(String productId, int quantity) {
        if (quantity <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }

        String userId = userService.getCurrentUserId();
        Cart cart = findOrCreateCart(userId);

        CartProduct cartProduct = cart.getCartProducts().stream()
                .filter(cp -> cp.getProduct().getId().toString().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        cartProduct.setQuantity(quantity);
        datastore.save(cartProduct);
        return mapToDTO(cart);
    }

    @Override
    public CartDTO removeProduct(String productId) {
        String userId = userService.getCurrentUserId();
        Cart cart = findOrCreateCart(userId);

        CartProduct productToRemove = cart.getCartProducts().stream()
                .filter(cp -> cp.getProduct().getId().toString().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        cart.getCartProducts().remove(productToRemove);
        datastore.delete(productToRemove);
        datastore.save(cart);
        return mapToDTO(cart);
    }

    @Override
    public void clearCart() {
        String userId = userService.getCurrentUserId();
        Cart cart = findOrCreateCart(userId);
        cart.getCartProducts().forEach(datastore::delete);
        cart.setCartProducts(new ArrayList<>());
        datastore.save(cart);
    }

    private Cart findOrCreateCart(String userId) {
        User user = datastore.find(User.class)
                .filter(Filters.eq("_id", new ObjectId(userId)))
                .first();

        Cart cart = datastore.find(Cart.class)
                .filter(Filters.eq("user", user))
                .first();

        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setCartProducts(new ArrayList<>());
            datastore.save(cart);
        }

        return cart;
    }

    private CartDTO mapToDTO(Cart cart) {
        CartDTO dto = new CartDTO();
        dto.setId(cart.getId().toString());
        dto.setUser(userService.getProfile());
        dto.setProducts(cart.getCartProducts().stream()
                .map(this::mapToCartProductDTO)
                .collect(java.util.stream.Collectors.toList()));
        return dto;
    }

    private CartProductDTO mapToCartProductDTO(CartProduct cartProduct) {
        CartProductDTO dto = new CartProductDTO();
        dto.setId(cartProduct.getId().toString());
        dto.setCartId(cartProduct.getCartId());
        dto.setProduct(cartProduct.getProduct());
        dto.setQuantity(cartProduct.getQuantity());
        return dto;
    }
}
