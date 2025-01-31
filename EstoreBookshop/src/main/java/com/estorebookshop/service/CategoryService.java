package com.estorebookshop.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.estorebookshop.model.Category;

public interface CategoryService {

	public Category findById(Long id);
	
	public List<Category> findAll();

	public Category save(Category category);

	public void deleteById(Long id);

	public List<Category> findByKeyword(String keyword);

	public Page<Category> findAll(Integer pageno);

	public Page<Category> findByKeyword(String keyword, Integer pageNo);
	
	public List<Category> findAllById(List<Long> categoryIds);
	
}
