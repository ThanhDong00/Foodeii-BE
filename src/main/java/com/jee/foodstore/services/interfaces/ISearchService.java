package com.jee.foodstore.services.interfaces;

import com.jee.foodstore.models.Product;

import java.util.List;

public interface ISearchService {
    List<Product> searchProduct(String productName);
}
