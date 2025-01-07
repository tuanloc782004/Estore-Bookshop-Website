package com.estorebookshop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="cart_items")
public class CartItem {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "id_cart", referencedColumnName = "id", nullable = false)
    private Cart cart;
	
	@ManyToOne
    @JoinColumn(name = "id_book", referencedColumnName = "id", nullable = false)
    private Book book;
	
	@Column(name = "quantity", nullable = false)
	private Long quantity;

	public CartItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CartItem(Long id, Cart cart, Book book, Long quantity) {
		super();
		this.id = id;
		this.cart = cart;
		this.book = book;
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

}
