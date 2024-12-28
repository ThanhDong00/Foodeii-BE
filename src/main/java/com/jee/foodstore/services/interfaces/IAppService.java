package com.jee.foodstore.services.interfaces;

import com.jee.foodstore.models.BaseEntity;
import org.bson.types.ObjectId;

import java.util.List;

public interface IAppService<T extends BaseEntity> {
    T create(T category);
    T findById(ObjectId id);
    List<T> findAll();
    T update(ObjectId id, T category);
    void delete(ObjectId id);
}
