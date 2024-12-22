package com.jee.foodstore.resources;

import com.jee.foodstore.dtos.CategoryDTO;
import com.jee.foodstore.mapper.CategoryDTOMapper;
import com.jee.foodstore.models.Category;
import com.jee.foodstore.services.CategoryService;
import com.jee.foodstore.services.interfaces.ICategoryService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;

import java.util.List;

@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryResource {
    private ICategoryService categoryService = new CategoryService();

    @POST
    public Response createCategory(Category category) {
        try {
            Category created = categoryService.create(category);
            CategoryDTO categoryDTO = CategoryDTOMapper.mapToDTO(created);
            return Response.status(Response.Status.CREATED).entity(categoryDTO).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getCategoryById(@PathParam("id") String id) {
        try {
            Category category = categoryService.findById(new ObjectId(id));
            CategoryDTO categoryDTO = CategoryDTOMapper.mapToDTO(category);
            return Response.ok(categoryDTO).build();
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

    @GET
    public Response getAllCategories() {
        List<Category> categories = categoryService.findAll();
        return Response.ok(categories).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateCategory(@PathParam("id") String id, Category category) {
        try {
            Category updated = categoryService.update(new ObjectId(id), category);
            return Response.ok(updated).build();
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

    @DELETE
    @Path("/{id}")
    public Response deleteCategory(@PathParam("id") String id) {
        try {
            categoryService.delete(new ObjectId(id));
            return Response.noContent().build();
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
