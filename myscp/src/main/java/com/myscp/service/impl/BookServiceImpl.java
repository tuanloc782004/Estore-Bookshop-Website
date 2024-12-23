package com.myscp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.myscp.model.Book;
import com.myscp.repository.BookRepository;
import com.myscp.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;
	
	@Override
	public Book createBook(Book book) {
		return this.bookRepository.save(book);
	}

	@Override
	public Book getBookById(Long id) {
		return this.bookRepository.findById(id).orElse(null);
	}

	@Override
	public Book updateBook(Book book) {
		return this.bookRepository.save(book);
	}

	@Override
	public void deleteBook(Long id) {
		this.bookRepository.deleteById(id);
	}

	@Override
	public List<Book> searchBook(String keyword) {
		return this.bookRepository.searchBook(keyword);
	}

	@Override
	public Page<Book> getAll(Integer pageno) {
		Pageable pageable = PageRequest.of(pageno - 1, 5);
		return this.bookRepository.findAll(pageable);
	}

	@Override
	public Page<Book> searchBook(String keyword, Integer pageno) {
		List<Book> list = this.searchBook(keyword);
		Pageable pageable = PageRequest.of(pageno - 1, 5);
		Integer start = (int) pageable.getOffset();
		Integer end = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size()
				: pageable.getOffset() + pageable.getPageSize());
		list = list.subList(start, end);
		return new PageImpl<Book>(list, pageable, this.searchBook(keyword).size());
	}

}
