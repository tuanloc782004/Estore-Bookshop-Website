package com.estorebookshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.estorebookshop.model.Language;

public interface LanguageRepository extends JpaRepository<Language, Long> {

	@Query("SELECT l FROM Language l WHERE l.languageName LIKE %?1%")
    List<Language> findByKeyword(String keyword);
	
}
