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
import org.springframework.web.bind.annotation.RestController;

import com.mtp.restapipro.exception.ResourceNotFoundException;
import com.mtp.restapipro.models.Tag;
import com.mtp.restapipro.models.Tutorial;
import com.mtp.restapipro.repository.ITagRepository;
import com.mtp.restapipro.repository.ITutorialRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class TagController {
	
	@Autowired
	private ITutorialRepository tutorialRepository;
	
	@Autowired
	private ITagRepository tagRepository;
	
	@GetMapping("/tags")
	public ResponseEntity<List<Tag>> getAllTags(){
		List<Tag> tags = new ArrayList<Tag>();
		tagRepository.findAll().forEach(tags::add);
		if(tags.isEmpty()){
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(tags, HttpStatus.OK);
	}
	
	@GetMapping("/tutorials/{tutorialId}/tags")
	public ResponseEntity<List<Tag>> getAllTagsByTutorialId(@PathVariable(value = "tutorialId") Long tutorialId){
		if(!tutorialRepository.existsById(tutorialId)){
			throw new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId);
		}
		
		List<Tag> tags = tagRepository.findTagsByTutorialsId(tutorialId);
		return new ResponseEntity<>(tags, HttpStatus.OK);
	}
	
	@GetMapping("/tags/{id}")
	public ResponseEntity<Tag> getTagById(@PathVariable(value = "id") Long id){
		Tag tag = tagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Tag with id = " + id));
		return new ResponseEntity<>(tag, HttpStatus.OK);
	}
	
	@GetMapping("/tags/{tagId}/tutorials")
	public ResponseEntity<List<Tutorial>> getAllTutorialsByTagId(@PathVariable(value = "tagId") Long tagId){
		if(!tagRepository.existsById(tagId)){
			throw new ResourceNotFoundException("Not found Tag with id = " + tagId);
		}
		List<Tutorial> tutorials = tutorialRepository.findTutorialsByTagsId(tagId);
		return new ResponseEntity<>(tutorials, HttpStatus.OK);
	}
	
	@PostMapping("/tutorials/{tutorialId}/tags")
	public ResponseEntity<Tag> addTag(@PathVariable(value = "tutorialId") Long tutorialId, @RequestBody Tag tagRequest){
		Tag tag = tutorialRepository.findById(tutorialId).map(tutorial -> {
			long tagId = tagRequest.getId();
			
			// can kiem tra ky xem tagRequest co id hay khong co id nua !!!
			
			// neu tagRequest co id
			// tag is existed
			if(tagId != 0L){
				Tag _tag = tagRepository.findById(tagId)
						.orElseThrow(() -> new ResourceNotFoundException("Not found Tag with id = " + tagId));
				tutorial.addTag(_tag);
				tutorialRepository.save(tutorial);
				return _tag;
			}
			
			// add and create new Tag
			
			Tag _tag = tagRepository.save(tagRequest);
			tutorial.addTag(_tag);
			tutorialRepository.save(tutorial);
			return _tag;
			
//			tutorial.addTag(tagRequest);
//			return tagRepository.save(tagRequest);
		}).orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId));
		return new ResponseEntity<>(tag, HttpStatus.CREATED);
	}
	
	@PutMapping("/tags/{id}")
	public ResponseEntity<Tag> updateTag(@PathVariable(value = "id") Long id, @RequestBody Tag tagRequest){
		Tag tag = tagRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("TagId " + id + " not found"));
		tag.setName(tagRequest.getName());
		return new ResponseEntity<>(tagRepository.save(tag), HttpStatus.OK);
	}
	
	@DeleteMapping("/tutorials/{tutorialId}/tags/{tagId}")
	public ResponseEntity<HttpStatus> deleteTagFromTutorial(@PathVariable(value = "tutorialId") Long tutorialId, @PathVariable(value="tagId") Long tagId){
		Tutorial tutorial = tutorialRepository.findById(tutorialId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId));
		tutorial.removeTag(tagId);
		// update lai tutorial moi sau khi xoa
		tutorialRepository.save(tutorial);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/tags/{id}")
	public ResponseEntity<HttpStatus> deleteTag(@PathVariable("id") Long id){
		tagRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
