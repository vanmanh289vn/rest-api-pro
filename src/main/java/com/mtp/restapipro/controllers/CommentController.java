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
import com.mtp.restapipro.models.Comment;
import com.mtp.restapipro.models.Tutorial;
import com.mtp.restapipro.repository.ICommentRepository;
import com.mtp.restapipro.repository.ITutorialRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class CommentController {
	@Autowired
	private ITutorialRepository tutorialRepository;
	
	@Autowired
	private ICommentRepository commentRepository;
	
	@GetMapping("/tutorials/{tutorialId}/comments")
	public ResponseEntity<List<Comment>> getAllCommentsByTutorialId(@PathVariable(value = "tutorialId") Long tutorialId){
//		if(!tutorialRepository.existsById(tutorialId)){
//			throw new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId);
//		}
		
//		List<Comment> comments = commentRepository.findByTutorialId(tutorialId);
		
		Tutorial tutorial = tutorialRepository.findById(tutorialId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId));
		
		List<Comment> comments = new ArrayList<Comment>();
		comments.addAll(tutorial.getComments());
		
		
		
		return new ResponseEntity<>(comments, HttpStatus.OK);
	}
	
	@GetMapping("/comments/{id}")
	public ResponseEntity<Comment> getCommentById(@PathVariable(value = "id") Long id){
		Comment comment = commentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Comment with id = " + id));
		return new ResponseEntity<>(comment, HttpStatus.OK);
	}
	
	@PutMapping("/comments/{id}")
	  public ResponseEntity<Comment> updateComment(@PathVariable("id") long id, @RequestBody Comment commentRequest) {
	    Comment comment = commentRepository.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("CommentId " + id + "not found"));

	    comment.setContent(commentRequest.getContent());

	    return new ResponseEntity<>(commentRepository.save(comment), HttpStatus.OK);
	  }
	
	@DeleteMapping("/comments/{id}")
	  public ResponseEntity<HttpStatus> deleteComment(@PathVariable("id") long id) {
	    commentRepository.deleteById(id);

	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  }
	
	@PostMapping("/tutorials/{tutorialId}/comments")
	public ResponseEntity<Comment> createComment(@PathVariable(value = "tutorialId") Long tutorialId, @RequestBody Comment commentRequest){
//		Comment comment = tutorialRepository.findById(tutorialId).map(tutorial -> {
//			commentRequest.setTutorial(tutorial);
//			return commentRepository.save(commentRequest);
//		}).orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId));
		
		Comment comment = tutorialRepository.findById(tutorialId).map(tutorial -> {
			tutorial.getComments().add(commentRequest);
			return commentRepository.save(commentRequest);
		}).orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId));
		
		return new ResponseEntity<>(comment, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/tutorials/{tutorialId}/comments")
	public ResponseEntity<HttpStatus> deleteAllCommentsOfTutorial(@PathVariable(value = "tutorialId") Long tutorialId){
//		if(!tutorialRepository.existsById(tutorialId)){
//			throw new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId);
//		}
//		commentRepository.deleteByTutorialId(tutorialId);
		
		Tutorial tutorial = tutorialRepository.findById(tutorialId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId));
		tutorial.removeComments();
		tutorialRepository.save(tutorial);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
