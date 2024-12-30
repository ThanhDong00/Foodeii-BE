package com.jee.foodstore.models;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Reference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity("orders")
@EqualsAndHashCode(callSuper = true)
public class Order extends BaseEntity {
    @Reference
    private User user;

    private BigDecimal totalAmount;
    private String status;
    @Reference
    private List<OrderProduct> orderProducts;
}
