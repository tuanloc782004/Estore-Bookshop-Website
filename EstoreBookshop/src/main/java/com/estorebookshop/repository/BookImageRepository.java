package com.estorebookshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estorebookshop.model.Book;
import com.estorebookshop.model.BookImage;

public interface BookImageRepository extends JpaRepository<BookImage, Long> {

	void deleteByBook(Book book);
	
}
