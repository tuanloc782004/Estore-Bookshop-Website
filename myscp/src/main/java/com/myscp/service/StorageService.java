package com.myscp.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	void init();

    void store(MultipartFile file, String avatarUrl); 
}