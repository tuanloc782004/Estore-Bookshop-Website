package com.estorebookshop.model;

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
@Table(name="publishers")
public class Publisher {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "publisher_name", nullable = false, unique = true, length = 100)
	private String publisherName;
	
	@OneToMany(mappedBy = "publisher", fetch = FetchType.EAGER)
	private Set<Book> books;

	public Publisher() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Publisher(Long id, String publisherName, Set<Book> books) {
		super();
		this.id = id;
		this.publisherName = publisherName;
		this.books = books;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}

}
