package com.estorebookshop.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.estorebookshop.model.Category;
import com.estorebookshop.repository.CategoryRepository;
import com.estorebookshop.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Category findById(Long id) {
		// TODO Auto-generated method stub
		return this.categoryRepository.findById(id).orElse(null);
	}

	@Override
	public Category save(Category category) {
		// TODO Auto-generated method stub
		return this.categoryRepository.save(category);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		this.categoryRepository.deleteById(id);
	}

	@Override
	public List<Category> findByKeyword(String keyword) {
		// TODO Auto-generated method stub
		return this.categoryRepository.findByKeyword(keyword);
	}

	@Override
	public Page<Category> findAll(Integer pageno) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(pageno - 1, 8);
		return this.categoryRepository.findAll(pageable);
	}

	@Override
	public Page<Category> findByKeyword(String keyword, Integer pageNo) {
		// TODO Auto-generated method stub
		List<Category> list = this.categoryRepository.findByKeyword(keyword);
		Pageable pageable = PageRequest.of(pageNo - 1, 8);
		Integer start = (int) pageable.getOffset();
		Integer end = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size()
				: pageable.getOffset() + pageable.getPageSize());
		list = list.subList(start, end);
		return new PageImpl<Category>(list, pageable, this.findByKeyword(keyword).size());
	}
}
