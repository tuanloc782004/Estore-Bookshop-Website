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
@Table(name="oder_details")
public class OderDetail {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "id_oder", referencedColumnName = "id", nullable = false)
    private Oder oder;
	
	@ManyToOne
    @JoinColumn(name = "id_book", referencedColumnName = "id", nullable = false)
    private Book book;
	
	@Column(name = "quantity", nullable = false)
	private Long quantity;
	
	@Column(name = "price", nullable = false)
	private Long price;

	public OderDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OderDetail(Long id, Oder oder, Book book, Long quantity, Long price) {
		super();
		this.id = id;
		this.oder = oder;
		this.book = book;
		this.quantity = quantity;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Oder getOder() {
		return oder;
	}

	public void setOder(Oder oder) {
		this.oder = oder;
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

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

}
