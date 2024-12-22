package com.jee.foodstore.services;

import com.jee.foodstore.models.Category;
import com.jee.foodstore.services.interfaces.ICategoryService;
import com.jee.foodstore.utils.MongoDBConnect;
import dev.morphia.Datastore;
import dev.morphia.query.filters.Filters;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

import java.util.List;

@Slf4j
@ApplicationScoped
public class CategoryService implements ICategoryService {
    private final Datastore datastore;

    public CategoryService() {
        this.datastore = MongoDBConnect.getDatastore();
    }

    @Override
    public Category create(Category category) {
        if(category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        try {
            datastore.save(category);
            log.info("Created category: {}", category.getId());
            return category;
        } catch (Exception e) {
            log.error("Error creating category: {}", e.getMessage());
            throw new RuntimeException("Failed to create category", e);
        }
    }

    @Override
    public Category findById(ObjectId id) {
        Category category = datastore.find(Category.class)
                .filter(Filters.eq("_id", id))
                .first();
        if (category == null) {
            log.warn("Category not found with id: {}", id);
            throw new RuntimeException("Category not found");
        }
        return category;
    }

    @Override
    public List<Category> findAll() {
        return datastore.find(Category.class)
                .iterator()
                .toList();
    }

    @Override
    public Category update(ObjectId id, Category category) {
        Category existing = findById(id);
        existing.setName(category.getName());
        existing.setDescription(category.getDescription());

        try {
            datastore.save(existing);
            log.info("Updated category: {}", existing.getId());
            return existing;
        } catch (Exception e) {
            log.error("Error updating category: {}", e.getMessage());
            throw new RuntimeException("Failed to update category", e);
        }
    }

    @Override
    public void delete(ObjectId id) {
        try {
            Category existing = findById(id);
            datastore.delete(existing);
            log.info("Deleted category: {}", id);
        } catch (Exception e) {
            log.error("Error deleting category: {}", e.getMessage());
            throw new RuntimeException("Failed to delete category", e);
        }
    }
}
