package com.estorebookshop.service;

import com.estorebookshop.model.BookImage;

public interface BookImageService {

	public BookImage save(BookImage bookImage);
	
	public void deleteByBookId(Long id);
	
}
