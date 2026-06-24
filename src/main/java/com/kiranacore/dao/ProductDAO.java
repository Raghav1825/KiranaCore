package com.kiranacore.dao;

import com.kiranacore.model.Product;
import java.util.List;

public class ProductDAO {
    void addProduct(Product product);

    Product getProductById(int productId);
    List <Product> getAllProducts();
    
    void updateStock(int productId,double quantity);
    List <Product> getLowStockAlert();
}
