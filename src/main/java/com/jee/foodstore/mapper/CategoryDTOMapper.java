package com.jee.foodstore.mapper;

import com.jee.foodstore.dtos.CategoryDTO;
import com.jee.foodstore.models.Category;

public class CategoryDTOMapper {
    public static CategoryDTO mapToDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.set_id(category.getId().toString());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setCreatedAt(category.getCreatedAt());
        dto.setUpdatedAt(category.getUpdatedAt());
        return dto;
    }
}
