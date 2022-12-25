package com.mtp.restapipro.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mtp.restapipro.models.Comment;

public interface ICommentRepository extends JpaRepository<Comment, Long> {
	
//	List<Comment> findByTutorialId(Long postId);
	
//	@Transactional
//	void deleteByTutorialId(Long tutorialId);

}
