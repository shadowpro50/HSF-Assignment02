package com.se192491.assignment02.service;

import com.se192491.assignment02.pojo.Category;

import java.util.List;

public interface CategoryService {
    public void save(Category category);
    public Category findById(int id);
    public List<Category> findAll();
    public Category update(int id,Category category);
    public void delete(int id);
}
