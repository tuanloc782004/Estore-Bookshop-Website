package com.estorebookshop.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.estorebookshop.model.Book;
import com.estorebookshop.model.BookCategory;
import com.estorebookshop.repository.BookCategoryRepository;
import com.estorebookshop.service.BookCategoryService;

@Service
public class BookCategoryServiceImpl implements BookCategoryService {

	@Autowired
	private BookCategoryRepository bookCategoryRepository;
	
	@Override
	@Transactional
	public void deleteByBook(Book book) {
		// TODO Auto-generated method stub
		this.bookCategoryRepository.deleteByBook(book);
	}

	@Override
	public BookCategory save(BookCategory bookCategory) {
		// TODO Auto-generated method stub
		return this.bookCategoryRepository.save(bookCategory);
	}

}
