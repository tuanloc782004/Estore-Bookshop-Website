package com.estorebookshop.model;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="categories")
public class Category {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "category_name", nullable = false, unique = true, length = 100)
	private String categoryName;
	
	@Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createdAt;
	
	@OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
	private Set<BookCategory> bookCategories;

	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Category(Long id, String categoryName, LocalDateTime createdAt, Set<BookCategory> bookCategories) {
		super();
		this.id = id;
		this.categoryName = categoryName;
		this.createdAt = createdAt;
		this.bookCategories = bookCategories;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Set<BookCategory> getBookCategories() {
		return bookCategories;
	}

	public void setBookCategories(Set<BookCategory> bookCategories) {
		this.bookCategories = bookCategories;
	}

}
