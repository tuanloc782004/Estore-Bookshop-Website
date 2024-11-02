package com.myscp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.myscp.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

	@Query("SELECT DISTINCT b FROM Book b " +
		       "WHERE b.title LIKE %?1% " +
		       "OR b.author LIKE %?1% " +
		       "OR b.publisher LIKE %?1% ")
		public List<Book> searchBook(String keyWord);

}
