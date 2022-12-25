package com.mtp.restapipro.service.impl;

import java.io.IOException;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.mtp.restapipro.models.FileDB;
import com.mtp.restapipro.repository.FileDBRepository;

@Service
public class FileStorageService {
	
	@Autowired
	private FileDBRepository fileDBRepository;
	
	public FileDB store(MultipartFile file) throws IOException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		FileDB fileDB = new FileDB(fileName, file.getContentType(), file.getBytes());
		
		return fileDBRepository.save(fileDB);
	}
	
	public FileDB getFile(String id){
		return fileDBRepository.findById(id).get();
	}
	
	public Stream<FileDB> getAllFiles(){
		return fileDBRepository.findAll().stream();
	}

}
