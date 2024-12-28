package com.jee.foodstore.resources;

import com.jee.foodstore.models.BaseEntity;
import com.jee.foodstore.services.AppService;
import com.jee.foodstore.services.interfaces.IAppService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;

import java.util.List;

public abstract class BaseResource<T extends BaseEntity> {
    private IAppService<T> appService;

    public BaseResource(Class<T> getClass) {
        this.appService = new AppService<>(getClass);
    }

    @GET
    public Response getAll() {
        List<T> items = appService.findAll();
        return Response.ok(items).build();
    }

    @POST
    public Response create(T item) {
        try {
            T created = appService.create(item);
            return Response
                    .status(Response.Status.CREATED)
                    .entity(created)
                    .build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") String id) {
        try {
            T item = appService.findById(new ObjectId(id));
            return Response.ok(item).build();
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

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") String id, T item) {
        try {
            T updated = appService.update(new ObjectId(id), item);
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
    public Response delete(@PathParam("id") String id) {
        try {
            appService.delete(new ObjectId(id));
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
