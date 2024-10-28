package com.myscp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myscp.model.Language;

public interface LanguageRepository extends JpaRepository<Language, Long> {

}
