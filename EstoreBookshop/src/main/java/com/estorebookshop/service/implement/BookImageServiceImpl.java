package com.estorebookshop.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estorebookshop.model.BookImage;
import com.estorebookshop.repository.BookImageRepository;
import com.estorebookshop.service.BookImageService;

@Service
public class BookImageServiceImpl implements BookImageService {

	@Autowired
	private BookImageRepository bookImageRepository;

	@Override
	public BookImage save(BookImage bookImage) {
		// TODO Auto-generated method stub
		return this.bookImageRepository.save(bookImage);
	}

	@Override
	public void deleteByBookId(Long id) {
		// TODO Auto-generated method stub
		this.bookImageRepository.deleteByBookId(id);;
	}

}
