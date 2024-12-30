package com.jee.foodstore.resources;

import com.jee.foodstore.dtos.user.*;
import com.jee.foodstore.services.UserService;
import com.jee.foodstore.services.interfaces.IUserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    @Inject
    private IUserService userService;
//    private IUserService userService = new UserService();

    @POST
    @Path("/signup")
    public Response signUp(SignUpDTO signUpDTO) {
        try {
            UserDTO createdUser = userService.signUp(signUpDTO);
            return Response.status(Response.Status.CREATED).entity(createdUser).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/signin")
    public Response signIn(SignInDTO signInDTO) {
        try {
            SignInResponseDTO response = userService.signIn(signInDTO);
            return Response.ok(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET

    public Response getAllUsers() {
        try {
            return Response.ok(userService.getAllUsers()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/profile")
    public Response getProfile() {
        try {
            return Response.ok(userService.getProfile()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/profile")
    public Response updateProfile(UserDTO updatedUser) {
        try {
            UserDTO updatedUserDTO = userService.updateProfile(updatedUser);
            return Response.ok(updatedUserDTO).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/profile/change-password")
    public Response changePassword(ChangePwdRequestDTO changePwdRequestDTO) {
        try {
            ChangePwdResponseDTO response = userService.changePassword(changePwdRequestDTO);
            return Response.ok(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
