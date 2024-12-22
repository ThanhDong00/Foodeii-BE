package com.jee.foodstore.dtos;

import lombok.Data;

@Data
public class CategoryDTO {
    private String _id;  // MongoDB format
    private String name;
    private String description;
    private String createdAt;
    private String updatedAt;
}
