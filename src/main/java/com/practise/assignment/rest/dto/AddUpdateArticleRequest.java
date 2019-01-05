package com.practise.assignment.rest.dto;

import java.time.LocalDateTime;
import java.util.List;

public class AddUpdateArticleRequest {

	private String header;
	private String shortDescription;
	private String text;
	private LocalDateTime publishDate;
	private List<Long> authors;
	private List<Long> keywords;

	private String createdBy;
	private LocalDateTime creationTime;
	private String updatedBy;
	private LocalDateTime updationTime;

	public String getHeader() {
		return header;
	}

	public AddUpdateArticleRequest setHeader(String header) {
		this.header = header;
		return this;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public AddUpdateArticleRequest setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
		return this;
	}

	public String getText() {
		return text;
	}

	public AddUpdateArticleRequest setText(String text) {
		this.text = text;
		return this;
	}

	public LocalDateTime getPublishDate() {
		return publishDate;
	}

	public AddUpdateArticleRequest setPublishDate(LocalDateTime publishDate) {
		this.publishDate = publishDate;
		return this;
	}

	public List<Long> getAuthors() {
		return authors;
	}

	public AddUpdateArticleRequest setAuthors(List<Long> authors) {
		this.authors = authors;
		return this;
	}

	public List<Long> getKeywords() {
		return keywords;
	}

	public AddUpdateArticleRequest setKeywords(List<Long> keywords) {
		this.keywords = keywords;
		return this;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public AddUpdateArticleRequest setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
		return this;
	}

	public LocalDateTime getCreationTime() {
		return creationTime;
	}

	public AddUpdateArticleRequest setCreationTime(LocalDateTime creationTime) {
		this.creationTime = creationTime;
		return this;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public AddUpdateArticleRequest setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
		return this;
	}

	public LocalDateTime getUpdationTime() {
		return updationTime;
	}

	public AddUpdateArticleRequest setUpdationTime(LocalDateTime updationTime) {
		this.updationTime = updationTime;
		return this;
	}

}
