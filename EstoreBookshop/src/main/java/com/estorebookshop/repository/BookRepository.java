package com.estorebookshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.estorebookshop.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

	@Query("SELECT b FROM Book b WHERE b.isbn LIKE %?1% OR b.title LIKE %?1% OR b.author LIKE %?1%")
	List<Book> findByKeyword(String keyword);

	@Query("SELECT b FROM Book b " + "JOIN b.language l " + "JOIN b.bookCategories bc " + "JOIN bc.category c "
			+ "WHERE (:languageId IS NULL OR l.id = :languageId) " + "AND (:categoryId IS NULL OR c.id = :categoryId) "
			+ "AND b.enabled = true " + "ORDER BY CASE " + "WHEN :sort = 'popular' THEN b.rating END DESC, "
			+ "CASE WHEN :sort = 'best' THEN b.soldQuantity END DESC, "
			+ "CASE WHEN :sort = 'new' THEN b.createdAt END DESC, " + "CASE WHEN :sort = 'lh' THEN b.price END ASC, "
			+ "CASE WHEN :sort = 'hl' THEN b.price END DESC")
	List<Book> findForHome(@Param("languageId") Long languageId, @Param("categoryId") Long categoryId,
			@Param("sort") String sort);

	@Query("SELECT b FROM Book b " + "WHERE b.enabled = true "
			+ "AND (:keyword IS NULL OR :keyword = '' OR b.isbn LIKE %:keyword% OR b.title LIKE %:keyword% OR b.author LIKE %:keyword%) "
			+ "ORDER BY CASE " + "WHEN :sort = 'popular' THEN b.rating END DESC, "
			+ "CASE WHEN :sort = 'best' THEN b.soldQuantity END DESC, "
			+ "CASE WHEN :sort = 'new' THEN b.createdAt END DESC, " + "CASE WHEN :sort = 'lh' THEN b.price END ASC, "
			+ "CASE WHEN :sort = 'hl' THEN b.price END DESC")
	List<Book> findForSearch(@Param("keyword") String keyword, @Param("sort") String sort);

	long count();

}
