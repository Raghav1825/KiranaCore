package com.kiranacore.dao;

import com.kiranacore.config.DatabaseConfig;
import com.kiranacore.model.Category;
import java.sql.*;
import java.util.*;

public class CategoryDAOImpl implements CategoryDAO {
    
    @Override
    public void addCategory(Category category){
        String query = "INSERT INTO categories (category_name) VALUES (?)";
        try(Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, category.getCategoryName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding category");
            e.printStackTrace();
        }
    }

    @Override
    public void updateCategoryDetails(Category category, String name){
        String query = "UPDATE categories SET category_name = ? WHERE category_id = ?";
        try(Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, category.getCategoryId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating category");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCategory(int categoryId){
        String query = "DELETE FROM categories WHERE category_id = ?";
        try(Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, categoryId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting category");
            e.printStackTrace();
        }
    }

    @Override
    public List<Category> getAllCategories(){
        String query = "SELECT * FROM categories";
        List<Category> categories = new ArrayList<>();
        try(Connection connection = DatabaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                categories.add(new Category(
                    resultSet.getInt("category_id"),
                    resultSet.getString("category_name")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error getting all categories");
            e.printStackTrace();
        }
        return categories;
    }

}
