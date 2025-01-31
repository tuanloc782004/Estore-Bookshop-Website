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
@Table(name = "users")
public class User {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "id_role", referencedColumnName = "id", nullable = false)
    private Role role;
	
	@Column(name = "username", nullable = false, unique = true, length = 50)
	private String username;
	
	@Column(name = "password", nullable = false, length = 255)
	private String password;
	
	@Column(name = "email", nullable = false, unique = true, length = 50)
	private String email;
	
	@Column(name = "phone_number", nullable = false, length = 15, unique = true)
	private String phoneNumber;
	
	@Column(name = "avatar_url", length = 255, nullable = true)
	private String avatarUrl = "/upload-dir/default-avatar/user-avatar.jfif";
	
	@Column(name = "enabled", nullable = false)
	private boolean enabled;
	
	@Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createdAt = LocalDateTime.now();
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private Set<Address> addresses;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private Set<Order> oders;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private Set<Review> reviews;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private Set<Cart> carts;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(Long id, Role role, String username, String password, String email, String phoneNumber,
			String avatarUrl, boolean enabled, LocalDateTime createdAt, Set<Address> addresses, Set<Order> oders,
			Set<Review> reviews, Set<Cart> carts) {
		super();
		this.id = id;
		this.role = role;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.avatarUrl = avatarUrl;
		this.enabled = enabled;
		this.createdAt = createdAt;
		this.addresses = addresses;
		this.oders = oders;
		this.reviews = reviews;
		this.carts = carts;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
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

	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	public Set<Order> getOders() {
		return oders;
	}

	public void setOders(Set<Order> oders) {
		this.oders = oders;
	}

	public Set<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	public Set<Cart> getCarts() {
		return carts;
	}

	public void setCarts(Set<Cart> carts) {
		this.carts = carts;
	}

}