package com.jee.foodstore.resources;

import com.jee.foodstore.models.OrderProduct;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/order-products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderProductResource extends BaseResource<OrderProduct> {
    public OrderProductResource() {
        super(OrderProduct.class);
    }
}
