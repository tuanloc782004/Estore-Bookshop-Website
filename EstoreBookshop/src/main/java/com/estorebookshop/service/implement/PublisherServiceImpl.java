package com.estorebookshop.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.estorebookshop.model.Publisher;
import com.estorebookshop.repository.PublisherRepository;
import com.estorebookshop.service.PublisherService;

@Service
public class PublisherServiceImpl implements PublisherService {

	@Autowired
	private PublisherRepository publisherRepository;

	@Override
	public Publisher findById(Long id) {
		// TODO Auto-generated method stub
		return this.publisherRepository.findById(id).orElse(null);
	}

	@Override
	public Publisher save(Publisher publisher) {
		// TODO Auto-generated method stub
		return this.publisherRepository.save(publisher);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		this.publisherRepository.deleteById(id);
	}

	@Override
	public List<Publisher> findByKeyword(String keyword) {
		// TODO Auto-generated method stub
		return this.publisherRepository.findByKeyword(keyword);
	}

	@Override
	public Page<Publisher> findAll(Integer pageno) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(pageno - 1, 8);
		return this.publisherRepository.findAll(pageable);
	}

	@Override
	public Page<Publisher> findByKeyword(String keyword, Integer pageNo) {
		// TODO Auto-generated method stub
		List<Publisher> list = this.publisherRepository.findByKeyword(keyword);
		Pageable pageable = PageRequest.of(pageNo - 1, 8);
		Integer start = (int) pageable.getOffset();
		Integer end = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size()
				: pageable.getOffset() + pageable.getPageSize());
		list = list.subList(start, end);
		return new PageImpl<Publisher>(list, pageable, this.findByKeyword(keyword).size());
	}
}
