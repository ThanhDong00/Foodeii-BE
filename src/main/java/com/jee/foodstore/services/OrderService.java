package com.jee.foodstore.services;

import com.jee.foodstore.dtos.cart.CartProductDTO;
import com.jee.foodstore.dtos.order.CreateOrderDTO;
import com.jee.foodstore.models.*;
import com.jee.foodstore.services.interfaces.IOrderService;
import com.jee.foodstore.utils.MongoDBConnect;
import dev.morphia.Datastore;
import dev.morphia.query.filters.Filters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequestScoped
public class OrderService implements IOrderService {
    private final HttpHeaders headers;
    private final Datastore datastore;
    private final UserService userService;

    @Inject
    public OrderService(@Context HttpHeaders headers, UserService userService) {
        this.datastore = MongoDBConnect.getDatastore();
        this.headers = headers;
        this.userService = userService;
    }

    @Override
    public Order createOrder(CreateOrderDTO createOrderDTO) {
        String userId = userService.getCurrentUserId();

        User user = datastore.find(User.class)
                .filter(Filters.eq("_id", new ObjectId(userId)))
                .first();

        Order order = new Order();
        order.setUser(user);
        order.setStatus(createOrderDTO.getStatus());
        order.setTotalAmount(createOrderDTO.getTotalAmount());
        order.setOrderProducts(new ArrayList<>());

        datastore.save(order);

        for(CartProductDTO productDTO : createOrderDTO.getProducts()) {
            Product product = datastore.find(Product.class)
                    .filter(Filters.eq("_id", new ObjectId(productDTO.getProduct().getId())))
                    .first();

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrderId(order.getId());
            orderProduct.setProduct(product);
            orderProduct.setQuantity(productDTO.getQuantity());
            assert product != null;
            orderProduct.setPrice(product.getPrice());
            datastore.save(orderProduct);

            order.getOrderProducts().add(orderProduct);
        }

        datastore.save(order);
        return order;
    }

    @Override
    public List<Order> getByUserId() {
        String userId = userService.getCurrentUserId();

        User user = datastore.find(User.class)
                .filter(Filters.eq("_id", new ObjectId(userId)))
                .first();

        var orders = datastore.find(Order.class)
                .filter(Filters.eq("user", user))
                .iterator()
                .toList();
        return orders;
    }


}
