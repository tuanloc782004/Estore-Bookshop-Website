package com.myscp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="category_books")
public class CategoryBook {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_category",referencedColumnName = "id", nullable = false)
	private Category category;
	
	@ManyToOne
	@JoinColumn(name = "id_book",referencedColumnName = "id", nullable = false)
	private Book book;

	public CategoryBook() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CategoryBook(Long id, Category category, Book book) {
		super();
		this.id = id;
		this.category = category;
		this.book = book;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
	
}
