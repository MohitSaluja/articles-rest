package com.practise.assignment.dao.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;

@Entity(name = "articles")
public class ArticleEntity extends BaseEntity {

	@Size(max = 500)
	private String header;
	@Size(max = 500)
	private String shortDescription;
	@Size(max = 5000)
	private String text;
	private LocalDateTime publishDate;

	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "article_author", joinColumns = { @JoinColumn(name = "article_id") }, inverseJoinColumns = {
			@JoinColumn(name = "author_id") })
	private Set<AuthorEntity> authors = new HashSet<>();

	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "article_keyword", joinColumns = { @JoinColumn(name = "article_id") }, inverseJoinColumns = {
			@JoinColumn(name = "keyword_id") })
	private Set<KeywordEntity> keywords = new HashSet<>();

	public String getHeader() {
		return header;
	}

	public ArticleEntity setHeader(String header) {
		this.header = header;
		return this;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public ArticleEntity setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
		return this;
	}

	public String getText() {
		return text;
	}

	public ArticleEntity setText(String text) {
		this.text = text;
		return this;
	}

	public LocalDateTime getPublishDate() {
		return publishDate;
	}

	public ArticleEntity setPublishDate(LocalDateTime publishDate) {
		this.publishDate = publishDate;
		return this;
	}

	public Set<AuthorEntity> getAuthors() {
		return authors;
	}

	public ArticleEntity setAuthors(Set<AuthorEntity> authors) {
		this.authors = authors;
		return this;
	}

	public Set<KeywordEntity> getKeywords() {
		return keywords;
	}

	public ArticleEntity setKeywords(Set<KeywordEntity> keywords) {
		this.keywords = keywords;
		return this;
	}
}
