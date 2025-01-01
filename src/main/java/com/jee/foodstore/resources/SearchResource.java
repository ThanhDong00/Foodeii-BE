package com.jee.foodstore.resources;

import com.jee.foodstore.services.interfaces.ISearchService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/search")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SearchResource {
    @Inject
    private ISearchService searchService;

//    @GET
//    @Path("?key={keyword}")
//    public Response search(@QueryParam("key") String keyword) {
//        try {
//            return Response.ok(searchService.searchProduct(keyword)).build();
//        } catch (Exception e) {
//            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
//        }
//    }

    @GET
    public Response search(@QueryParam("keyword") String keyword) {
        try {
            if (keyword == null || keyword.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Search keyword is required")
                        .build();
            }
            return Response.ok(searchService.searchProduct(keyword)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error occurred: " + e.getMessage())
                    .build();
        }
    }
}
