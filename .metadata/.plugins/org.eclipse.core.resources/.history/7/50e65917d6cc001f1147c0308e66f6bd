package com.myscp.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.myscp.service.StorageService;

@Service
public class StorageServiceImpl implements StorageService {

	private final Path rootLocation;

	public StorageServiceImpl() {
		// TODO Auto-generated constructor stub
		this.rootLocation = Paths.get("src/main/resources/static/upload-dir");
	}
	
	@Override
	public void store(MultipartFile file, String avatarUrl) {
	    try {
	        Path destinationFile = this.rootLocation.resolve(Paths.get(avatarUrl)).normalize()
	                .toAbsolutePath();
	        try (InputStream inputStream = file.getInputStream()) {
	            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}


	@Override
	public void init() {
		// TODO Auto-generated method stub
		try {
			Files.createDirectories(rootLocation);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}