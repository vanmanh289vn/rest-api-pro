package com.mtp.restapipro.controllers;

import java.util.Date;

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
import org.springframework.web.bind.annotation.RestController;

import com.mtp.restapipro.exception.ResourceNotFoundException;
import com.mtp.restapipro.models.Tutorial;
import com.mtp.restapipro.models.TutorialDetails;
import com.mtp.restapipro.repository.ITutorialDetailsRepository;
import com.mtp.restapipro.repository.ITutorialRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class TutorialDetailsController {
	
	@Autowired
	private ITutorialDetailsRepository detailsRepository;
	
	@Autowired
	private ITutorialRepository tutorialRepository;
	
	@GetMapping({ "/details/{id}", "/tutorials/{id}/details" })
	public ResponseEntity<TutorialDetails> getDetailsById(@PathVariable(value = "id") Long id){
		TutorialDetails details = detailsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial Details with id = " + id));
		return new ResponseEntity<>(details, HttpStatus.OK);
	}
	
	@PostMapping("/tutorials/{tutorialId}/details")
	public ResponseEntity<TutorialDetails> createDetails(@PathVariable(value = "tutorialId") Long tutorialId, @RequestBody TutorialDetails detailsRequest){
		Tutorial tutorial = tutorialRepository.findById(tutorialId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId));
		detailsRequest.setCreatedOn(new Date());
		detailsRequest.setTutorial(tutorial);
		TutorialDetails details = detailsRepository.save(detailsRequest);
		
		return new ResponseEntity<>(details, HttpStatus.CREATED);
	}
	
	@PutMapping("/details/{id}")
	public ResponseEntity<TutorialDetails> updateDetails(@PathVariable(value = "id") Long id, @RequestBody TutorialDetails detailsRequest){
		TutorialDetails details = detailsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Id " + id + " not found"));
		details.setCreatedBy(detailsRequest.getCreatedBy());
		return new ResponseEntity<>(detailsRepository.save(details), HttpStatus.OK);
	}
	
	@DeleteMapping("/details/{id}")
	public ResponseEntity<HttpStatus> deleteDetails(@PathVariable(value = "id") Long id){
		detailsRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/tutorials/{tutorialId}/details")
	public ResponseEntity<TutorialDetails> deleteDetailsOfTutorial(@PathVariable(value = "tutorialId") Long tutorialId){
		if(!tutorialRepository.existsById(tutorialId)){
			throw new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId);
		}
		
		detailsRepository.deleteByTutorialId(tutorialId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
