package com.estorebookshop.model;

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
@Table(name="oders")
public class Oder {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id", nullable = false)
    private User user;
	
	@Column(name = "total_price", nullable = false)
	private Long totalPrice;
	
	@Column(name = "status", nullable = false, length = 50)
	private String status;
	
	@Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createdAt;
	
	@OneToMany(mappedBy = "oder", fetch = FetchType.EAGER)
	private Set<OderDetail> oderDetails;

	public Oder() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Oder(Long id, User user, Long totalPrice, String status, LocalDateTime createdAt,
			Set<OderDetail> oderDetails) {
		super();
		this.id = id;
		this.user = user;
		this.totalPrice = totalPrice;
		this.status = status;
		this.createdAt = createdAt;
		this.oderDetails = oderDetails;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Set<OderDetail> getOderDetails() {
		return oderDetails;
	}

	public void setOderDetails(Set<OderDetail> oderDetails) {
		this.oderDetails = oderDetails;
	}
	
}
