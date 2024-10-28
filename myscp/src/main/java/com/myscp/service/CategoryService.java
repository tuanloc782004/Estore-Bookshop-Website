package com.myscp.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.myscp.model.Category;

public interface CategoryService {

	public Category createCategory(Category category);
	public List<Category> getAllCategories();
	public Category getCategoryById(Long id);
	public Category updateCategory(Category category);
	public void deleteCategory(Long id);
	
	public List<Category> searchCategory(String keyword);
	
	public Page<Category> getAll(Integer pageno);
	public Page<Category> searchCategory(String keyword, Integer pageNo);
	
}
