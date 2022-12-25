package com.mtp.restapipro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mtp.restapipro.models.Tag;

public interface ITagRepository extends JpaRepository<Tag, Long> {
	
	List<Tag> findTagsByTutorialsId(Long tutorialId);
}
