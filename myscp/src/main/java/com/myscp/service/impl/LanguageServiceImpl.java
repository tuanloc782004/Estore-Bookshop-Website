package com.myscp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myscp.model.Language;
import com.myscp.repository.LanguageRepository;
import com.myscp.service.LanguageService;

@Service
public class LanguageServiceImpl implements LanguageService {

	@Autowired
	private LanguageRepository languageRepository;
	
	@Override
	public Language createLanguage(Language language) {
		return languageRepository.save(language);
	}
	
	@Override
	public List<Language> getAllLanguages() {
		return languageRepository.findAll();
	}
	
	@Override
	public Language getLanguageById(Long id) {
		return languageRepository.findById(id).orElse(null);
	}
	
	@Override
	public Language updateLanguage(Language language) {
		return languageRepository.save(language);
	}
	
	@Override
	public void deleteLanguage(Long id) {
		languageRepository.deleteById(id);
	}
}
