package com.myscp.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.myscp.model.Book;

public interface BookService {

	public Book createBook(Book book);

	public Book getBookById(Long id);

	public Book updateBook(Book book);

	public void deleteBook(Long id);

	public List<Book> searchBook(String keyword);

	public Page<Book> getAll(Integer pageno);

	public Page<Book> searchBook(String keyword, Integer pageno);

}
