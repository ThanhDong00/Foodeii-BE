package com.jee.foodstore.services;

import com.jee.foodstore.models.Order;
import com.jee.foodstore.services.interfaces.IOrderService;
import com.jee.foodstore.utils.MongoDBConnect;
import dev.morphia.Datastore;
import dev.morphia.query.filters.Filters;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
public class OrderService implements IOrderService {
    private final Datastore datastore;

    public OrderService() {
        this.datastore = MongoDBConnect.getDatastore();
    }

    @Override
    public List<Order> getByUserId() {
        var userId = "123";
        var orders = datastore.find(Order.class)
                .filter(Filters.eq("user.id", userId))
                .iterator()
                .toList();
        return orders;
    }
}
