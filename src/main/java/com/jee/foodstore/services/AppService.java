package com.jee.foodstore.services;

import com.jee.foodstore.models.BaseEntity;
import com.jee.foodstore.services.interfaces.IAppService;
import com.jee.foodstore.utils.MongoDBConnect;
import dev.morphia.Datastore;
import dev.morphia.query.filters.Filters;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

import java.util.List;

@Slf4j
@ApplicationScoped
public class AppService<T extends BaseEntity> implements IAppService<T> {
    private final Datastore datastore;
    private final Class<T> getClass;

    public AppService(Class<T> getClass) {
        this.datastore = MongoDBConnect.getDatastore();
        this.getClass = getClass;
    }

    @Override
    public T create(T item) {
        if(item == null) {
            throw new IllegalArgumentException("T cannot be null");
        }
        try {
            datastore.save(item);
            log.info("Created item: {}", item.getId());
            return item;
        } catch (Exception e) {
            log.error("Error creating item: {}", e.getMessage());
            throw new RuntimeException("Failed to create item", e);
        }
    }

    @Override
    public T findById(ObjectId id) {
        T item = datastore.find(getClass)
                .filter(Filters.eq("_id", id))
                .first();
        if (item == null) {
            log.warn("T not found with id: {}", id);
            throw new RuntimeException("T not found");
        }
        return item;
    }

    @Override
    public List<T> findAll() {
        return datastore.find(getClass)
                .iterator()
                .toList();
    }

    @Override
    public T update(ObjectId id, T item) {
        item.setId(id);

        try {
            datastore.save(item);
            log.info("Updated item: {}", item.getId());
            return item;
        } catch (Exception e) {
            log.error("Error updating item: {}", e.getMessage());
            throw new RuntimeException("Failed to update item", e);
        }
    }

    @Override
    public void delete(ObjectId id) {
        try {
            T existing = findById(id);
            datastore.delete(existing);
            log.info("Deleted item: {}", id);
        } catch (Exception e) {
            log.error("Error deleting item: {}", e.getMessage());
            throw new RuntimeException("Failed to delete item", e);
        }
    }
}
