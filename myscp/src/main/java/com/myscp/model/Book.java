package com.myscp.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="books")
public class Book {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "id_language", referencedColumnName = "id", nullable = false)
    private Language language;
	
	@Column(name = "title", nullable = false, length = 100)
	private String title;
	
	@Column(name = "author", nullable = false, length = 50)
	private String author;
	
	@Column(name = "publisher", nullable = false, length = 100)
	private String publisher;
	
	@Column(name = "year_of_publication", nullable = false)
	private Integer yearOfPublication;
	
	@Column(name = "description", nullable = false, columnDefinition = "TEXT")
	private String description;
	
	@Column(name = "num_of_pages", nullable = false)
	private Long numOfPages;
	
	@Column(name = "quantity", nullable = false)
	private Long quantity;
	
	@Column(name = "price", nullable = false, precision = 12, scale = 2)
	private BigDecimal price;
	
	@Column(name = "discount", nullable = false, precision = 4, scale = 2)
	private BigDecimal discount;
	
	@Column(name = "enable", nullable = false)
	private boolean enabled;
	
	@Column(name = "create_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createdAt;
	
	@OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
	private Set<CategoryBook> bookCategories;
	
	@OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
	private Set<Image> images;

	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Book(Long id, Language language, String title, String author, String publisher, Integer yearOfPublication,
			String description, Long numOfPages, Long quantity, BigDecimal price, BigDecimal discount, boolean enabled,
			LocalDateTime createdAt, Set<CategoryBook> bookCategories, Set<Image> images) {
		super();
		this.id = id;
		this.language = language;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.yearOfPublication = yearOfPublication;
		this.description = description;
		this.numOfPages = numOfPages;
		this.quantity = quantity;
		this.price = price;
		this.discount = discount;
		this.enabled = enabled;
		this.createdAt = createdAt;
		this.bookCategories = bookCategories;
		this.images = images;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Integer getYearOfPublication() {
		return yearOfPublication;
	}

	public void setYearOfPublication(Integer yearOfPublication) {
		this.yearOfPublication = yearOfPublication;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getNumOfPages() {
		return numOfPages;
	}

	public void setNumOfPages(Long numOfPages) {
		this.numOfPages = numOfPages;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Set<CategoryBook> getBookCategories() {
		return bookCategories;
	}

	public void setBookCategories(Set<CategoryBook> bookCategories) {
		this.bookCategories = bookCategories;
	}

	public Set<Image> getImages() {
		return images;
	}

	public void setImages(Set<Image> images) {
		this.images = images;
	}
	
}