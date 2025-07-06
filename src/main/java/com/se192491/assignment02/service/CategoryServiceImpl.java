package com.se192491.assignment02.service;

import com.se192491.assignment02.pojo.Category;
import com.se192491.assignment02.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public Category findById(int id) {
        return categoryRepository.findById(id)
                .orElse(null);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category update(int id, Category category) {
        Category oldCategory = findById(id);
        if (oldCategory == null) {
            return null;
        }
        oldCategory.setCategoryName(category.getCategoryName());
        return categoryRepository.save(oldCategory);
    }

    @Override
    public void delete(int id) {
        categoryRepository.deleteById(id);
    }
}
