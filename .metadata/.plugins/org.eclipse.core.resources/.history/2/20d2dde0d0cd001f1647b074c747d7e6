package com.estorebookshop.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.estorebookshop.model.Publisher;

public interface PublisherService {

	public Publisher findById(Long id);

	public Publisher save(Publisher publisher);

	public void deleteById(Long id);

	public List<Publisher> findByKeyword(String keyword);

	public Page<Publisher> findAll(Integer pageno);

	public Page<Publisher> findByKeyword(String keyword, Integer pageNo);
	
}
