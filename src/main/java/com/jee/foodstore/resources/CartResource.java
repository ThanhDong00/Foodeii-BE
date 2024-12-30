package com.jee.foodstore.resources;

import com.jee.foodstore.dtos.cart.AddCartProductDTO;
import com.jee.foodstore.dtos.cart.CartProductDTO;
import com.jee.foodstore.services.interfaces.ICartService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/cart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartResource {
    @Inject
    private ICartService cartService;

    @GET
    public Response getCart() {
        try {
            return Response.ok(cartService.getCart()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/products")
    public Response addProduct(AddCartProductDTO product) {
        try {
            return Response.ok(cartService.addProduct(product)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/products/{productId}")
    public Response updateQuantity(@PathParam("productId") String productId, @QueryParam("quantity") int quantity) {
        try {
            return Response.ok(cartService.updateQuantity(productId, quantity)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/products/{productId}")
    public Response removeProduct(@PathParam("productId") String productId) {
        try {
            return Response.ok(cartService.removeProduct(productId)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    public Response clearCart() {
        try {
            cartService.clearCart();
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
