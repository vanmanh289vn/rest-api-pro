package com.mtp.restapipro.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mtp.restapipro.models.Tutorial;

public interface ITutorialRepository extends JpaRepository<Tutorial, Long> {
	List<Tutorial> findByPublished(boolean published);
	List<Tutorial> findByTitleContaining(String title);
	
	List<Tutorial> findTutorialsByTagsId(Long tagId);
	
	// Start : Derived Query
	
	Optional<Tutorial> findById(long id);
	List<Tutorial> findByLevel(int level);
	// or
	List<Tutorial> findByLevelIs(int level);
	// or
	List<Tutorial> findByLevelEquals(int level);
	
	List<Tutorial> findByLevelNot(int level);
	// or
	List<Tutorial> findByLevelIsNot(int level);
	
	List<Tutorial> findByLevelAndPublished(int level, boolean isPublished);
	List<Tutorial> findByTitleOrDescription(String title, String description);
	
	// List<Tutorial> findByTitleLike(String title);
	List<Tutorial> findByTitleStartingWith(String title);
	List<Tutorial> findByTitleEndingWith(String title);
	// List<Tutorial> findByTitleContaining(String title);
	List<Tutorial> findByTitleContainingOrDescriptionContaining(String title, String description);
	List<Tutorial> findByTitleContainingAndPublished(String title, boolean isPublished);
	
	List<Tutorial> findByTitleContainingIgnoreCase(String title);
	
	List<Tutorial> findByPublishedTrue();
	List<Tutorial> findByPublishedFalse();
	
	List<Tutorial> findByLevelGreaterThan(int level);
	List<Tutorial> findByCreatedAtGreaterThanEqual(Date date);
	List<Tutorial> findByCreatedAtAfter(Date date);
	// List<Tutorial> findByLevelBetween(int start, int end);
	List<Tutorial> findByLevelBetweenAndPublished(int start, int end, boolean isPublished);
	List<Tutorial> findByCreatedAtBetween(Date start, Date end);
	
	List<Tutorial> findByOrderByLevel();
	// or
	List<Tutorial> findByOrderByLevelAsc();
	List<Tutorial> findByOrderByLevelDesc();
	List<Tutorial> findByTitleContainingOrderByLevelDesc(String title);
	List<Tutorial> findByPublishedOrderByCreatedAtDesc(boolean isPublished);
	
	List<Tutorial> findByTitleContaining(String title, Sort sort);
	List<Tutorial> findByPublished(boolean isPublished, Sort sort);
	
	Page<Tutorial> findAll(Pageable pageable);
	Page<Tutorial> findByTitle(String title, Pageable pageable);
	
	@Transactional
	void deleteAllByCreatedAtBefore(Date date);
	
	// End : Derived Query
	
	//Select Query with where condition
	
	@Query("SELECT t FROM Tutorial t")
	List<Tutorial> findAllQuery();
	
	@Query("SELECT t FROM Tutorial t WHERE t.published=?1")
	List<Tutorial> findByPublishedQuery(boolean isPublished);
	
	@Query("SELECT t FROM Tutorial t WHERE t.title LIKE %?1%")
	List<Tutorial> findByTitleLike(String title);
	
	@Query("SELECT t FROM Tutorial t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', ?1,'%'))")
	List<Tutorial> findByTitleLikeCaseInsensitive(String title);
	
	// JPA query greater than or equal to
	
	@Query("SELECT t FROM Tutorial t WHERE t.level >= ?1")
	List<Tutorial> findByLevelGreaterThanEqual(int level);
	
	@Query("SELECT t FROM Tutorial t WHERE t.createdAt >= ?1")
	List<Tutorial> findByDateGreaterThanEqual(Date date);
	
	// JPA Query Between
	
	@Query("SELECT t FROM Tutorial t WHERE t.level BETWEEN ?1 AND ?2")
	List<Tutorial> findByLevelBetween(int start, int end);
	
	@Query("SELECT t FROM Tutorial t WHERE t.createdAt BETWEEN ?1 AND ?2")
	List<Tutorial> findByDateBetween(Date start, Date end);
	
	// JPA Query example with parameters
	
	@Query("SELECT t FROM Tutorial t WHERE t.published=:isPublished AND t.level BETWEEN :start AND :end")
	List<Tutorial> findByLevelBetweenParam(@Param("start") int start, @Param("end") int end, @Param("isPublished") boolean isPublished);
	
	// JPA Query Order By Desc/Asc
	
	@Query("SELECT t FROM Tutorial t ORDER BY t.level DESC")
	List<Tutorial> findAllOrderByLevelDesc();
	
	@Query("SELECT t FROM Tutorial t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', ?1,'%')) ORDER BY t.level ASC")
	List<Tutorial> findByTitleOrderByLevelAsc(String title);
	
	@Query("SELECT t FROM Tutorial t WHERE t.published=true ORDER BY t.createdAt DESC")
	List<Tutorial> findAllPublishedOrderByCreatedDesc();
	
	// JPA Query Sort By
	
	@Query("SELECT t FROM Tutorial t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', ?1,'%'))")
	List<Tutorial> findByTitleAndSort(String title, Sort sort);
	
	@Query("SELECT t FROM Tutorial t WHERE t.published=?1")
	List<Tutorial> findByPublishedAndSort(boolean isPublished, Sort sort);
	
	// JPA Query Pagination
	
	@Query("SELECT t FROM Tutorial t")
	Page<Tutorial> findAllWithPagination(Pageable pageable);
	
	// JPA Query Update
	
	@Transactional
	@Modifying
	@Query("UPDATE Tutorial t SET t.published=true WHERE t.id=?1")
	int publishTutorial(Long id);

}
