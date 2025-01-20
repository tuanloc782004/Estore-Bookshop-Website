package com.estorebookshop.model;

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
@Table(name="addresses")
public class Address {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id", nullable = false)
    private User user;
	
	@Column(name = "address", nullable = false, columnDefinition = "TEXT")
	private String address;
	
	@Column(name = "city", nullable = false, length = 50)
	private String city;
	
	@Column(name = "zip_code", nullable = false, length = 20)
	private String zipCode;
	
	@Column(name = "country", nullable = false, length = 50)
	private String country;
	
	@OneToMany(mappedBy = "address", fetch = FetchType.EAGER)
	private Set<Order> oder;

	public Address() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Address(Long id, User user, String address, String city, String zipCode, String country, Set<Order> oder) {
		super();
		this.id = id;
		this.user = user;
		this.address = address;
		this.city = city;
		this.zipCode = zipCode;
		this.country = country;
		this.oder = oder;
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

	public String getAddress() {
		return address;
	}

	public void setAddressName(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Set<Order> getOder() {
		return oder;
	}

	public void setOder(Set<Order> oder) {
		this.oder = oder;
	}

}
