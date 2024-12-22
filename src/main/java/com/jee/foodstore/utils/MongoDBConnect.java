package com.jee.foodstore.utils;

import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MongoDBConnect {
    private static final String MONGODB_CONNECTION = "mongodb+srv://thanhdong:8SOLiGqX1WrtSnSu@foodstore.nicmp.mongodb.net/";
    private static final String MONGODB_DATABASE = "store";

    private static Datastore datastore;

    private MongoDBConnect() {
        // Private constructor to prevent instantiation
    }

    public static Datastore getDatastore() {
        if (datastore == null) {
            try {
                datastore = createDatastore();
                validateConnection();
                log.info("MongoDB connection established successfully");
            } catch (Exception e) {
                log.error("Failed to connect to MongoDB: {}", e.getMessage());
                throw new RuntimeException("Could not connect to MongoDB", e);
            }
        }
        return datastore;
    }

    private static Datastore createDatastore() {
        if (MONGODB_CONNECTION == null || MONGODB_DATABASE == null) {
            throw new IllegalStateException("MongoDB connection details not found in environment variables");
        }
        return Morphia.createDatastore(
                MongoClients.create(MONGODB_CONNECTION),
                MONGODB_DATABASE);
    }

    private static void validateConnection() {
        datastore.getDatabase().runCommand(new org.bson.Document("ping", 1));
    }
}
