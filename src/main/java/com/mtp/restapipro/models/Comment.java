package com.mtp.restapipro.models;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "comments")
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Lob
	private String content;
	
	// Chu y : Day la truong hop su dung ManyToOne de lien ket Tutorial va Comment
	// Co the dung cach khac la su dung OneToMany de implement trong class Tutorial
	// Hai cach nay deu tuong duong nhau khi tao ra cung 1 tap cac APIs

	// @ManyToOne(fetch = FetchType.LAZY, optional = false)
	// @JoinColumn(name = "tutorial_id", nullable = false)
	// @OnDelete(action = OnDeleteAction.CASCADE)
	// @JsonIgnore
	// private Tutorial tutorial;

	public Comment() {
		super();
	}

	// public Comment(Long id, String content, Tutorial tutorial) {
	// super();
	// this.id = id;
	// this.content = content;
	// this.tutorial = tutorial;
	// }

	public Comment(Long id, String content) {
		super();
		this.id = id;
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	// public Tutorial getTutorial() {
	// return tutorial;
	// }
	//
	// public void setTutorial(Tutorial tutorial) {
	// this.tutorial = tutorial;
	// }

}
