package com.jee.foodstore.models;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Reference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity("carts")
@EqualsAndHashCode(callSuper = true)
public class Cart extends BaseEntity {
    @Reference
    private User user;
    @Reference (lazy = false, idOnly = true)
    private List<CartProduct> cartProducts = new ArrayList<>();
}
