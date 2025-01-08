package com.estorebookshop.service;

import com.estorebookshop.model.Book;
import com.estorebookshop.model.BookImage;

public interface BookImageService {

	public void deleteByBook(Book book);
	
	public BookImage save(BookImage bookImage);
	
}
