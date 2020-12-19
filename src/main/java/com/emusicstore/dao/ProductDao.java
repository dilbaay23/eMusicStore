package com.emusicstore.dao;

import com.emusicstore.model.Product;


import java.util.List;

/**
 * Created by Moon on 12/12/2020
 */


public interface ProductDao {

    void addProduct(Product product);

    void editProduct(Product product);

    Product getProductById(String id);

    List<Product> getALlProducts();


    void deleteProduct(String id);


}
