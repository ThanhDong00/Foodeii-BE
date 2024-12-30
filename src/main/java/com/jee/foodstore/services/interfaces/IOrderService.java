package com.jee.foodstore.services.interfaces;

import com.jee.foodstore.dtos.order.CreateOrderDTO;
import com.jee.foodstore.models.Order;
import org.bson.types.ObjectId;

import java.util.List;

public interface IOrderService {
    Order createOrder(CreateOrderDTO createOrderDTO);
    List<Order> getByUserId();
}
