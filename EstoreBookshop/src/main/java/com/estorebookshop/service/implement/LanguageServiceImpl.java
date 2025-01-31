package com.estorebookshop.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estorebookshop.model.Language;
import com.estorebookshop.repository.LanguageRepository;
import com.estorebookshop.service.LanguageService;

@Service
public class LanguageServiceImpl implements LanguageService{

	@Autowired
	private LanguageRepository languageRepository;
	
	@Override
	public Language findById(Long id) {
		// TODO Auto-generated method stub
		return this.languageRepository.findById(id).orElse(null);
	}

	@Override
	public Language save(Language language) {
		// TODO Auto-generated method stub
		return this.languageRepository.save(language);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		this.languageRepository.deleteById(id);
	}

	@Override
	public List<Language> findByKeyword(String keyword) {
		// TODO Auto-generated method stub
		return this.languageRepository.findByKeyword(keyword);
	}

	@Override
	public List<Language> findAll() {
		// TODO Auto-generated method stub
		return this.languageRepository.findAll();
	}

}
