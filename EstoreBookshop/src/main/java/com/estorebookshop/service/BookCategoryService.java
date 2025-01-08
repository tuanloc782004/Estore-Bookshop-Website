package com.estorebookshop.service;

import com.estorebookshop.model.Book;
import com.estorebookshop.model.BookCategory;

public interface BookCategoryService {
	
	public void deleteByBook(Book book);
	
	public BookCategory save(BookCategory bookCategory);
		
}
