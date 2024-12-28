package com.jee.foodstore.resources;

import com.jee.foodstore.models.Product;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource extends BaseResource<Product> {
    public ProductResource() {
        super(Product.class);
    }
}
