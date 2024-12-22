package com.jee.foodstore.models;

import dev.morphia.annotations.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity("users")
@EqualsAndHashCode(callSuper = true)
@Indexes({
        @Index(fields = @Field("email"), options = @IndexOptions(unique = true))
})
public class User extends BaseEntity{
    private String name;
    private String email;
    private String passwordHash;
    private String address;
    private String phone;
}


