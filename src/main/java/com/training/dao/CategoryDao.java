package com.training.dao;

import com.training.models.Category;

import java.util.List;

public interface CategoryDao {

    public Category getCategoryById(Long id);
    public List<Category> getAllCategories();
}
