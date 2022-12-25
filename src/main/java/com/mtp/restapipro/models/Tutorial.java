package com.mtp.restapipro.models;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(name="tutorials")
public class Tutorial {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="title")
	private String title;
	
	@Column(name="description")
	private String description;
	
	@Column(name="published")
	private boolean published;
	
	private int level;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@ManyToMany(fetch=FetchType.LAZY, 
			cascade= {
					CascadeType.PERSIST,
					CascadeType.MERGE
			})
	@JoinTable(name="tutorial_tags",
	joinColumns = {
			@JoinColumn(name = "tutorial_id")
	},
	inverseJoinColumns = {
			@JoinColumn(name = "tag_id")
	})
	private Set<Tag> tags = new HashSet<>();
	
	// Chu y : Day la truong hop su dung OneToMany unidirectional de lien ket Tutorial va Comment
	// Co the dung cach khac la su dung ManyToOne de implement trong class Comment
	// Hai cach nay deu tuong duong nhau khi tao ra cung 1 tap cac APIs
	
	// oneToMany unidirectional
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "tutorial_id")
	// @JsonIgnore   --> neu apply thi se ko hien thi cac comment trong tutorial
	@JsonIgnore
	private Set<Comment> comments = new HashSet<>();

	public Tutorial() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Tutorial(String title, String description, boolean published) {
		super();
		this.title = title;
		this.description = description;
		this.published = published;
	}
	
	public Tutorial(String title, String description, boolean published, int level, Date createdAt) {
		super();
		this.title = title;
		this.description = description;
		this.published = published;
		this.level = level;
		this.createdAt = createdAt;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}
	
	@Override
	public String toString() {
		return "Tutorial [id=" + id + ", title=" + title + ", desc=" + description + ", published=" + published + "]";
	}
	
	public void addTag(Tag tag){
		this.tags.add(tag);
		tag.getTutorials().add(this);
	}
	
	public void removeTag(long tagId){
		Tag tag = this.tags.stream().filter(t -> t.getId() == tagId).findFirst().orElse(null);
		if(tag != null){
			this.tags.remove(tag);
			tag.getTutorials().remove(this);
		}
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
	
	public void removeComments(){
		this.comments.clear();
	}
	
	

}
