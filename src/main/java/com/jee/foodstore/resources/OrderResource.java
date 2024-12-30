package com.jee.foodstore.resources;

import com.jee.foodstore.dtos.order.CreateOrderDTO;
import com.jee.foodstore.models.Order;
import com.jee.foodstore.services.OrderService;
import com.jee.foodstore.services.interfaces.IOrderService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource extends BaseResource<Order> {
    @Inject
    private IOrderService orderService;

    public OrderResource() {
        super(Order.class);
    }

    @GET
    @Path("/user")
    public Response getByUserId() {
        try {
            var items = orderService.getByUserId();
            return Response.ok(items).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid ID format")
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @POST
    @Path("/create")
    public Response createOrder(CreateOrderDTO dto) {
        try {
            var order = orderService.createOrder(dto);
            return Response.ok(order).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

}
