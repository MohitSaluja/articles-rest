package com.practise.assignment.dao.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity(name = "keywords")
public class KeywordEntity extends BaseEntity {

	private String name;

	public String getName() {
		return name;
	}

	public KeywordEntity setName(String name) {
		this.name = name;
		return this;
	}

	@ManyToMany(mappedBy = "keywords")
	private Set<ArticleEntity> articles = new HashSet<>();

	public Set<ArticleEntity> getArticles() {
		return articles;
	}

	public void setArticles(Set<ArticleEntity> articles) {
		this.articles = articles;
	}

}
