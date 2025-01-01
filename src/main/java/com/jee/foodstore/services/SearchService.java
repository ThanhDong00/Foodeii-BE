package com.jee.foodstore.services;


import com.jee.foodstore.models.Product;
import com.jee.foodstore.services.interfaces.ISearchService;
import com.jee.foodstore.utils.MongoDBConnect;
import dev.morphia.Datastore;
import dev.morphia.query.filters.Filters;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@RequestScoped
public class SearchService implements ISearchService {
    private final Datastore datastore;

    @Inject
    public SearchService() {
        this.datastore = MongoDBConnect.getDatastore();
    }

    @Override
    public List<Product> searchProduct(String productName) {
//        Pattern regexPattern = Pattern.compile(".*" + Pattern.quote(productName) + ".*", Pattern.CASE_INSENSITIVE);
//        List<Product> products = datastore.find(Product.class)
//                .filter(Filters.eq("name", regexPattern))
//                .iterator()
//                .toList();
//
//        return products;
        if (productName == null || productName.trim().isEmpty()) {
            return Collections.emptyList();
        }

        try {
            Pattern regexPattern = Pattern.compile(".*" + Pattern.quote(productName.trim()) + ".*", Pattern.CASE_INSENSITIVE);
            return datastore.find(Product.class)
                    .filter(Filters.eq("name", regexPattern))
                    .iterator()
                    .toList();
        } catch (Exception e) {
            log.error("Error searching for product: " + productName, e);
            throw new RuntimeException("Failed to search products", e);
        }
    }
}
