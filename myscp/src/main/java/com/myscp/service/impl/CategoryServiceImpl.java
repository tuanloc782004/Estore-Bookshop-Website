package com.myscp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.myscp.model.Category;
import com.myscp.repository.CategoryRepository;
import com.myscp.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public Category createCategory(Category category) {
		return this.categoryRepository.save(category);
	}

	@Override
	public Category getCategoryById(Long id) {
		return this.categoryRepository.findById(id).orElse(null);
	}

	@Override
	public Category updateCategory(Category category) {
		return this.categoryRepository.save(category);
	}

	@Override
	public void deleteCategory(Long id) {
		this.categoryRepository.deleteById(id);
	}
	
	@Override
	public List<Category> searchCategory(String keyword) {
		return this.categoryRepository.searchCategory(keyword);
	}

	@Override
	public Page<Category> getAll(Integer pageno) {
		Pageable pageable = PageRequest.of(pageno - 1, 5);
		return this.categoryRepository.findAll(pageable);
	}

	@Override
	public Page<Category> searchCategory(String keyword, Integer pageno) {
		List<Category> list = this.searchCategory(keyword);
		Pageable pageable = PageRequest.of(pageno - 1, 5);
		Integer start = (int) pageable.getOffset();
		Integer end = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size()
				: pageable.getOffset() + pageable.getPageSize());
		list = list.subList(start, end);
		return new PageImpl<Category>(list, pageable, this.searchCategory(keyword).size());
	}

	@Override
	public List<Category> getAllCategories() {
		return this.categoryRepository.findAll();
	}

}
