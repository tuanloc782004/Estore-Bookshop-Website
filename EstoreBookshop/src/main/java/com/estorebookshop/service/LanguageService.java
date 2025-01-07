package com.estorebookshop.service;

import java.util.List;

import com.estorebookshop.model.Language;

public interface LanguageService {

	public Language findById(Long id);

	public Language save(Language language);
	
	public List<Language> findAll();

	public void deleteById(Long id);

	public List<Language> findByKeyword(String keyword);
	
}
