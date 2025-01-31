package com.estorebookshop.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.estorebookshop.model.Book;
import com.estorebookshop.repository.BookRepository;
import com.estorebookshop.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;

	@Override
	public Book findById(Long id) {
		// TODO Auto-generated method stub
		return this.bookRepository.findById(id).orElse(null);
	}

	@Override
	public Book save(Book book) {
		// TODO Auto-generated method stub
		return this.bookRepository.save(book);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		this.bookRepository.deleteById(id);
	}

	@Override
	public List<Book> findByKeyword(String keyword) {
		// TODO Auto-generated method stub
		return this.bookRepository.findByKeyword(keyword);
	}

	@Override
	public Page<Book> findAll(Integer pageno) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(pageno - 1, 5);
		return this.bookRepository.findAll(pageable);
	}

	@Override
	public Page<Book> findByKeyword(String keyword, Integer pageNo) {
		// TODO Auto-generated method stub
		List<Book> list = this.bookRepository.findByKeyword(keyword);
		Pageable pageable = PageRequest.of(pageNo - 1, 5);
		Integer start = (int) pageable.getOffset();
		Integer end = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size()
				: pageable.getOffset() + pageable.getPageSize());
		list = list.subList(start, end);
		return new PageImpl<Book>(list, pageable, this.findByKeyword(keyword).size());
	}

	@Override
	public List<Book> findForHome(Long languageId, Long categoryId, String sort) {
		// TODO Auto-generated method stub
		return this.bookRepository.findForHome(languageId, categoryId, sort);
	}

	@Override
	public Page<Book> findAllForHome(Integer pageno) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(pageno - 1, 6);
		return this.bookRepository.findAll(pageable);
	}

	@Override
	public Page<Book> findForHome(Long languageId, Long categoryId, String sort, Integer pageNo) {
		// TODO Auto-generated method stub
		List<Book> list = this.bookRepository.findForHome(languageId, categoryId, sort);
		Pageable pageable = PageRequest.of(pageNo - 1, 6);
		Integer start = (int) pageable.getOffset();
		Integer end = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size()
				: pageable.getOffset() + pageable.getPageSize());
		list = list.subList(start, end);
		return new PageImpl<Book>(list, pageable, this.findForHome(languageId, categoryId, sort).size());
	}

	@Override
	public List<Book> findForSearch(String keyword, String sort) {
		// TODO Auto-generated method stub
		return this.bookRepository.findForSearch(keyword, sort);
	}

	@Override
	public Page<Book> findForSearch(String keyword, String sort, Integer pageNo) {
		// TODO Auto-generated method stub
		List<Book> list = this.bookRepository.findForSearch(keyword, sort);
		Pageable pageable = PageRequest.of(pageNo - 1, 6);
		Integer start = (int) pageable.getOffset();
		Integer end = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size()
				: pageable.getOffset() + pageable.getPageSize());
		list = list.subList(start, end);
		return new PageImpl<Book>(list, pageable, this.findForSearch(keyword, sort).size());
	}

	@Override
	public long countBooks() {
		// TODO Auto-generated method stub
		return this.bookRepository.count();
	}

}
