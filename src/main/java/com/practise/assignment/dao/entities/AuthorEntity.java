package com.practise.assignment.dao.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity(name = "authors")
public class AuthorEntity extends BaseEntity {

	private String name;

	public String getName() {
		return name;
	}

	public AuthorEntity setName(String name) {
		this.name = name;
		return this;
	}

	@ManyToMany(mappedBy = "authors")
	private Set<ArticleEntity> articles = new HashSet<>();

	public Set<ArticleEntity> getArticles() {
		return articles;
	}

	public void setArticles(Set<ArticleEntity> articles) {
		this.articles = articles;
	}

}
