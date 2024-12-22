package com.jee.foodstore.models;

import dev.morphia.annotations.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity("categories")
@EqualsAndHashCode(callSuper = true)
public class Category extends BaseEntity{
    private String name;
    private String description;
}
