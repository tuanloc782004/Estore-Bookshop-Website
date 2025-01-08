package com.estorebookshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.estorebookshop.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

	@Query("SELECT b FROM Book b WHERE b.isbn LIKE %?1% OR b.title LIKE %?1% OR b.author LIKE %?1%")
    List<Book> findByKeyword(String keyword);
	
}
