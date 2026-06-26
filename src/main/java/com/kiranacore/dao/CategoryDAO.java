package com.kiranacore.dao;

import com.kiranacore.model.Category;
import java.util.List;

public interface CategoryDAO {
    
    void addCategory(Category category);

    void updateCategoryDetails(Category category, String name);

    void deleteCategory(int categoryId);

    List<Category> getAllCategories();
}
