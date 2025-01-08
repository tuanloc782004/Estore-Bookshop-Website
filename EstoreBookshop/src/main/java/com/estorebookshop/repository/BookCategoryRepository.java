package com.estorebookshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estorebookshop.model.Book;
import com.estorebookshop.model.BookCategory;

public interface BookCategoryRepository extends JpaRepository<BookCategory, Long> {

	void deleteByBook(Book book);
	
}
