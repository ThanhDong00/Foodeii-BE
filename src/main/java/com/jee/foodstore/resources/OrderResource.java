package com.jee.foodstore.resources;

import com.jee.foodstore.models.Order;
import com.jee.foodstore.services.OrderService;
import com.jee.foodstore.services.interfaces.IOrderService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource extends BaseResource<Order> {
    private final IOrderService orderService = new OrderService();

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

}
