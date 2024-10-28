package com.myscp.service;

import java.util.List;

import com.myscp.model.Language;

public interface LanguageService {

	public Language createLanguage(Language language);
	public List<Language> getAllLanguages();
	public Language getLanguageById(Long id);
	public Language updateLanguage(Language language);
	public void deleteLanguage(Long id);
	
}
