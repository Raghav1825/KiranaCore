package com.kiranacore.dao;

import com.kiranacore.model.Product;
import com.kiranacore.config.DatabaseConfig;

import java.sql.*;
import java.util.*;

public class ProductDAOImpl implements ProductDAO {
    @Override
    public Product getProductById(int productId){
        String query = "SELECT * FROM products WHERE product_id=?";
        Product product = null;

        try{
            Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                product = new Product();
                product.setProductId(resultSet.getInt("product_id"));
                product.setName(resultSet.getString("name"));
                product.setUnit(resultSet.getString("unit"));
                product.setCurrentStock(resultSet.getDouble("current_stock"));
                product.setSellingPrice(resultSet.getDouble("selling_price"));
                product.setMrp(resultSet.getDouble("mrp"));
                product.setCategoryId(resultSet.getInt("category_id"));
            }
        }catch(SQLException e){
            System.out.println("Error in getting the product by Id");
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public void updateStock(int productId,double quantity){
        String query = "UPDATE products SET current_stock=current_stock-? WHERE product_id=?";

        try{
            Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setDouble(1, quantity);
            preparedStatement.setInt(2, productId);

            preparedStatement.executeUpdate();

        }catch(SQLException e){
            System.out.println("Error in updating the stock");
            e.printStackTrace();
        }
    }

    @Override
    public void addProduct(Product product){
        String query = "INSERT INTO products (name,category_id,unit,current_stock,min_stock_alert,mrp,selling_price) VALUES(?,?,?,?,?,?,?)";
        
        try{
            Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getCategoryId());
            preparedStatement.setString(3, product.getUnit());
            preparedStatement.setDouble(4, product.getCurrentStock());
            preparedStatement.setDouble(5, product.getMinStockAlert());
            preparedStatement.setDouble(6, product.getMrp());
            preparedStatement.setDouble(7, product.getSellingPrice());

            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println("Error in adding the product");
            e.printStackTrace();
        }
    }

    @Override
    public List <Product> getAllProducts(){
        String query = "SELECT * FROM products";
        List <Product> productsList = new ArrayList<>();

        try {
            Connection connection = DatabaseConfig.getConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                Product product = new Product();
                product.setProductId(resultSet.getInt("product_id"));
                product.setName(resultSet.getString("name"));
                product.setUnit(resultSet.getString("unit"));
                product.setCurrentStock(resultSet.getDouble("current_stock"));
                product.setSellingPrice(resultSet.getDouble("selling_price"));
                product.setMrp(resultSet.getDouble("mrp"));
                product.setCategoryId(resultSet.getInt("category_id"));
                productsList.add(product);
            }
        }catch(SQLException e) {
            System.out.println("Error in getting all the products");
            e.printStackTrace();
        }
        return productsList;
    }

    @Override
    public List <Product> getLowStockAlert(){
        String query = "SELECT * FROM products WHERE current_stock <= min_stock_alert";
        List <Product> productsList = new ArrayList<>();

        try{
            Connection connection = DatabaseConfig.getConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                Product product = new Product();
                product.setProductId(resultSet.getInt("product_id"));
                product.setName(resultSet.getString("name"));
                product.setUnit(resultSet.getString("unit"));
                product.setCurrentStock(resultSet.getDouble("current_stock"));
                product.setSellingPrice(resultSet.getDouble("selling_price"));
                product.setMrp(resultSet.getDouble("mrp"));
                product.setCategoryId(resultSet.getInt("category_id"));
                productsList.add(product);
            }
        }catch(SQLException e){
            System.out.println("Error in getting the low stock alert products");
            e.printStackTrace();
        }
        return productsList;
    }
}
