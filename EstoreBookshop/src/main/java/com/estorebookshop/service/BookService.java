package com.estorebookshop.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.estorebookshop.model.Book;

public interface BookService {

	public Book findById(Long id);

	public Book save(Book book);

	public void deleteById(Long id);

	public List<Book> findByKeyword(String keyword);

	public Page<Book> findAll(Integer pageno);

	public Page<Book> findByKeyword(String keyword, Integer pageNo);

	public List<Book> findForHome(Long languageId, Long categoryId, String sort);

	public Page<Book> findAllForHome(Integer pageno);

	public Page<Book> findForHome(Long languageId, Long categoryId, String sort, Integer pageNo);

	public List<Book> findForSearch(String keyword, String sort);

	public Page<Book> findForSearch(String keyword, String sort, Integer pageNo);
	
	public long countBooks();

}
