package com.estorebookshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.estorebookshop.model.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {

	@Query("SELECT p FROM Publisher p WHERE p.publisherName LIKE %?1%")
    List<Publisher> findByKeyword(String keyword);
	
}
