package com.jee.foodstore.models;

import dev.morphia.annotations.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity("users")
@EqualsAndHashCode(callSuper = true)
@Builder(toBuilder = true)
@Indexes({
        @Index(fields = @Field("email"), options = @IndexOptions(unique = true))
})
public class User extends BaseEntity{
    private String name;
    private String email;
    private String passwordHash;
    private String address;
    private String phone;

    @Builder.Default
    private String role = "USER";
}
