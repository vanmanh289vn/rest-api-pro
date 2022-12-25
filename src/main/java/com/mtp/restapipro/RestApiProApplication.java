package com.mtp.restapipro;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.mtp.restapipro.models.Tutorial;
import com.mtp.restapipro.repository.ITutorialRepository;
import com.mtp.restapipro.service.FilesStorageService;

@SpringBootApplication
public class RestApiProApplication implements CommandLineRunner {
	
	@Resource
	FilesStorageService storageService;
	
	@Autowired
	ITutorialRepository tutorialRepository;

	public static void main(String[] args) {
		SpringApplication.run(RestApiProApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		storageService.deleteAll();
		storageService.init();
		
		
		// Test Custom Query
		List<Tutorial> tutorials = new ArrayList<Tutorial>();
//		tutorials = tutorialRepository.findAllQuery();
//		tutorials = tutorialRepository.findByPublishedQuery(false);	
		
//		tutorials = tutorialRepository.findByTitleAndSort("at", Sort.by("level").descending());
//		tutorials = tutorialRepository.findByTitleAndSort("at", Sort.by("createdAt").descending());
		
//		tutorials = tutorialRepository.findByPublishedAndSort(false, Sort.by("level").descending());
		
		int page = 0;
		int size = 3;
//		Pageable pageable = PageRequest.of(page, size);
		Pageable pageable = PageRequest.of(page, size, Sort.by("level").descending());
		tutorials = tutorialRepository.findAllWithPagination(pageable).getContent();
		
		show(tutorials);
		
	}
	
	private void show(List<Tutorial> tutorials){
		tutorials.forEach(System.out::println);
	}

}
