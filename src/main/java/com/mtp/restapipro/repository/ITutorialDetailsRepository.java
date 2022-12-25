package com.mtp.restapipro.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mtp.restapipro.models.TutorialDetails;

public interface ITutorialDetailsRepository extends JpaRepository<TutorialDetails, Long> {
	@Transactional
	void deleteById(long id);
	
	@Transactional
	void deleteByTutorialId(long tutorialId);

}
