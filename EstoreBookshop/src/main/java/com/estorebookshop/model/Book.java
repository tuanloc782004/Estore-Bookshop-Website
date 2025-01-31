package com.estorebookshop.model;

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
	
	@ManyToOne
    @JoinColumn(name = "id_publisher", referencedColumnName = "id", nullable = false)
    private Publisher publisher;
	
	@Column(name = "isbn", nullable = false, length = 17, unique = true)
	private String isbn;
	
	@Column(name = "title", nullable = false, length = 100)
	private String title;
	
	@Column(name = "author", nullable = false, length = 50)
	private String author;

	@Column(name = "year_of_publication", nullable = false)
	private Integer yearOfPublication;
	
	@Column(name = "description", nullable = false, columnDefinition = "TEXT")
	private String description;
	
	@Column(name = "num_of_pages", nullable = false)
	private Long numOfPages;
	
	@Column(name = "quantity", nullable = false)
	private Long quantity;
	
	@Column(name = "sold_quantity", nullable = false)
	private Long soldQuantity;
	
	@Column(name = "price", nullable = false, precision = 10, scale = 2)
	private BigDecimal price;
	
	@Column(name = "discount", nullable = false, precision = 4, scale = 2)
	private BigDecimal discount;
	
	@Column(name = "rating", nullable = false, precision = 3, scale = 2)
	private BigDecimal rating;
	
	@Column(name = "enabled", nullable = false)
	private boolean enabled;
	
	@Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createdAt = LocalDateTime.now();
	
	@OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
	private Set<BookCategory> bookCategories;
	
	@OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
	private Set<BookImage> bookImages;
	
	@OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
	private Set<CartItem> cartItems;
	
	@OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
	private Set<Review> reviews;
	
	@OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
	private Set<OrderDetail> oderDetail;

	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Book(Long id, Language language, Publisher publisher, String isbn, String title, String author,
			Integer yearOfPublication, String description, Long numOfPages, Long quantity, Long soldQuantity,
			BigDecimal price, BigDecimal discount, BigDecimal rating, boolean enabled, LocalDateTime createdAt,
			Set<BookCategory> bookCategories, Set<BookImage> bookImages, Set<CartItem> cartItems, Set<Review> reviews,
			Set<OrderDetail> oderDetail) {
		super();
		this.id = id;
		this.language = language;
		this.publisher = publisher;
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.yearOfPublication = yearOfPublication;
		this.description = description;
		this.numOfPages = numOfPages;
		this.quantity = quantity;
		this.soldQuantity = soldQuantity;
		this.price = price;
		this.discount = discount;
		this.rating = rating;
		this.enabled = enabled;
		this.createdAt = createdAt;
		this.bookCategories = bookCategories;
		this.bookImages = bookImages;
		this.cartItems = cartItems;
		this.reviews = reviews;
		this.oderDetail = oderDetail;
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

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
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

	public Long getSoldQuantity() {
		return soldQuantity;
	}

	public void setSoldQuantity(Long soldQuantity) {
		this.soldQuantity = soldQuantity;
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

	public BigDecimal getRating() {
		return rating;
	}

	public void setRating(BigDecimal rating) {
		this.rating = rating;
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

	public Set<BookCategory> getBookCategories() {
		return bookCategories;
	}

	public void setBookCategories(Set<BookCategory> bookCategories) {
		this.bookCategories = bookCategories;
	}

	public Set<BookImage> getBookImages() {
		return bookImages;
	}

	public void setBookImages(Set<BookImage> bookImages) {
		this.bookImages = bookImages;
	}

	public Set<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(Set<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public Set<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	public Set<OrderDetail> getOderDetail() {
		return oderDetail;
	}

	public void setOderDetail(Set<OrderDetail> oderDetail) {
		this.oderDetail = oderDetail;
	}
	
}