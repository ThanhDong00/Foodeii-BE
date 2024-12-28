package com.jee.foodstore.models;

import dev.morphia.annotations.Id;
import dev.morphia.annotations.PrePersist;
import lombok.Data;
import org.bson.types.ObjectId;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public abstract class BaseEntity {
    @Id
    protected ObjectId id;
    protected String createdAt;
    protected String updatedAt;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = new ObjectId();
        }
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        createdAt = timestamp;
        updatedAt = timestamp;
    }

    public String getId() {
        return id.toString();
    }
}
