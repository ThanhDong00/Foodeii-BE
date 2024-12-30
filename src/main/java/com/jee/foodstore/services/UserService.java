package com.jee.foodstore.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jee.foodstore.dtos.user.*;
import com.jee.foodstore.models.User;
import com.jee.foodstore.services.interfaces.IUserService;
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
import org.mindrot.jbcrypt.BCrypt;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequestScoped
@Slf4j
public class UserService implements IUserService {
//    @Context
    private HttpHeaders headers;

    private final String secretKey = "secretKey";
    private final long expirationTime = 24 * 60 * 60 * 1000; // 24 giờ tính bằng milliseconds
    Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

    private final Datastore datastore;

    // Default constructor for CDI
    public UserService() {
        this.headers = null;
        this.datastore = MongoDBConnect.getDatastore();
    }

    @Inject
    public UserService(@Context HttpHeaders headers) {
        this.headers = headers;
        this.datastore = MongoDBConnect.getDatastore();
    }

    public String getCurrentUserId() {
        String authHeader = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("No authorization token provided");
        }

        String token = authHeader.substring(7);
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey)).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("id").asString();
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Invalid or expired token");
        }
    }

    @Override
    public UserDTO signUp(SignUpDTO signUpDTO) {
        // Check email
        if (datastore.find(User.class).filter(Filters.eq("email", signUpDTO.getEmail())).first() != null) {
            throw new RuntimeException("Email already exists");
        }

        // Create new user
        User user = User.builder()
                .email(signUpDTO.getEmail())
                .name(signUpDTO.getName())
                .passwordHash(BCrypt.hashpw(signUpDTO.getPassword(), BCrypt.gensalt()))
                .build();

        if (signUpDTO.getRole() != null) {
            user.setRole(signUpDTO.getRole());
        }

        try {
            datastore.save(user);
            return mapToDTO(user);
        } catch (Exception e) {
            log.error("Error creating user: {}", e.getMessage());
            throw new RuntimeException("Failed to create user", e);
        }
    }

    @Override
    public SignInResponseDTO signIn(SignInDTO signInDTO) {
        User user = datastore.find(User.class)
                .filter(Filters.eq("email", signInDTO.getEmail()))
                .first();

        if (user == null || !BCrypt.checkpw(signInDTO.getPassword(), user.getPasswordHash()) ) {
            throw new RuntimeException("Invalid email or password");
        }

        SignInResponseDTO response = new SignInResponseDTO();
        UserDTO userDTO = mapToDTO(user);
        response.setUser(userDTO);

        String token = JWT.create()
                .withClaim("id", userDTO.getId())
                .withClaim("role", userDTO.getRole())
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secretKey));
        response.setToken(token);

        return response;
    }

    @Override
    public UserDTO getProfile() {
        String userId = getCurrentUserId();
        User user = datastore.find(User.class)
                .filter(Filters.eq("_id", new ObjectId(userId)))
                .first();

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        return mapToDTO(user);
    }

    @Override
    public UserDTO updateProfile(UserDTO userDTO) {
        String userId = getCurrentUserId();
        User user = datastore.find(User.class)
                .filter(Filters.eq("_id", new ObjectId(userId)))
                .first();

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        user.setName(userDTO.getName());
        user.setAddress(userDTO.getAddress());
        user.setPhone(userDTO.getPhone());

        datastore.save(user);
        return mapToDTO(user);
    }

    @Override
    public ChangePwdResponseDTO changePassword(ChangePwdRequestDTO requestDTO) {
        String userId = getCurrentUserId();
        User user = datastore.find(User.class)
                .filter(Filters.eq("_id", new ObjectId(userId)))
                .first();

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if(!BCrypt.checkpw(requestDTO.getOldPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid old password");
        }

        user.setPasswordHash(BCrypt.hashpw(requestDTO.getNewPassword(), BCrypt.gensalt()));
        datastore.save(user);

        ChangePwdResponseDTO response = new ChangePwdResponseDTO();
        response.setMessage("Password changed successfully");
        response.setUser(mapToDTO(user));

        return response;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return datastore.find(User.class)
                .iterator()
                .toList()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private UserDTO mapToDTO(User user) {
        return new UserDTO(
                user.getId().toString(),
                user.getName(),
                user.getEmail(),
                user.getAddress(),
                user.getPhone(),
                user.getRole());
    }
}
