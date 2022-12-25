package com.mtp.restapipro.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mtp.restapipro.exception.ResourceNotFoundException;
import com.mtp.restapipro.models.Tutorial;
import com.mtp.restapipro.repository.ITutorialDetailsRepository;
import com.mtp.restapipro.repository.ITutorialRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class TutorialController {
	
	@Autowired
	ITutorialRepository tutorialRepository;
	
	@Autowired
	ITutorialDetailsRepository detailsRepository;
	
	//Test Query Custom
	@GetMapping("/tutorials_query")
	public ResponseEntity<List<Tutorial>> getAllTutorialsQuery(@RequestParam(required = false) String title){
		List<Tutorial> tutorials = new ArrayList<Tutorial>();
		if(title == null){
			tutorialRepository.findAllQuery().forEach(tutorials::add);
		} else {
			tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);
		}
		if(tutorials.isEmpty()){
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Tutorial>>(tutorials, HttpStatus.OK);
	}
	
	@GetMapping("/tutorials")
	public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title){
		List<Tutorial> tutorials = new ArrayList<Tutorial>();
		if(title == null){
			tutorialRepository.findAll().forEach(tutorials::add);
		} else {
			tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);
		}
		if(tutorials.isEmpty()){
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Tutorial>>(tutorials, HttpStatus.OK);
	}
	
	@GetMapping("/tutorials/{id}")
	public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") long id){
		Tutorial tutorial = tutorialRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("MTP said : Not found Tutorial with id = " + id));
		return new ResponseEntity<Tutorial>(tutorial, HttpStatus.OK);
	}
	
	@PostMapping("/tutorials")
	  public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
	    Tutorial _tutorial = tutorialRepository.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), false));
	    return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
	  }
	
	@PutMapping("/tutorials/{id}")
	  public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {
	    Tutorial _tutorial = tutorialRepository.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("MRJ Said : Not found Tutorial with id = " + id));

	    _tutorial.setTitle(tutorial.getTitle());
	    _tutorial.setDescription(tutorial.getDescription());
	    _tutorial.setPublished(tutorial.isPublished());
	    
	    return new ResponseEntity<>(tutorialRepository.save(_tutorial), HttpStatus.OK);
	  }
	
	@DeleteMapping("/tutorials/{id}")
	  public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
		
		if(detailsRepository.existsById(id)){
			detailsRepository.deleteById(id);
		}
		
	    tutorialRepository.deleteById(id);
	    
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  }
	
	@DeleteMapping("/tutorials")
	  public ResponseEntity<HttpStatus> deleteAllTutorials() {
	    tutorialRepository.deleteAll();
	    
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  }
	
	@GetMapping("/tutorials/published")
	  public ResponseEntity<List<Tutorial>> findByPublished() {
	    List<Tutorial> tutorials = tutorialRepository.findByPublished(true);

	    if (tutorials.isEmpty()) {
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }
	    
	    return new ResponseEntity<>(tutorials, HttpStatus.OK);
	  }

}
