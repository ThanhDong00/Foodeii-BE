package com.jee.foodstore.services.interfaces;

import com.jee.foodstore.models.Category;
import org.bson.types.ObjectId;

import java.util.List;

public interface ICategoryService {
    Category create(Category category);
    Category findById(ObjectId id);
    List<Category> findAll();
    Category update(ObjectId id, Category category);
    void delete(ObjectId id);
//    List<Category> findByName(String name);
}
