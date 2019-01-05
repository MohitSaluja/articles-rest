package com.practise.assignment.rest.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ArticleDTO {

	private Long id;
	private String header;
	private String shortDescription;
	private String text;
	private LocalDateTime publishDate;

	private List<ReferenceDTO> authors;

	private List<ReferenceDTO> keywords;

    public Long getId() {
        return id;
    }

    public ArticleDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getHeader() {
        return header;
    }

    public ArticleDTO setHeader(String header) {
        this.header = header;
        return this;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public ArticleDTO setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
        return this;
    }

    public String getText() {
        return text;
    }

    public ArticleDTO setText(String text) {
        this.text = text;
        return this;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public ArticleDTO setPublishDate(LocalDateTime publishDate) {
        this.publishDate = publishDate;
        return this;
    }

    public List<ReferenceDTO> getAuthors() {
        return authors;
    }

    public ArticleDTO setAuthors(List<ReferenceDTO> authors) {
        this.authors = authors;
        return this;
    }

    public List<ReferenceDTO> getKeywords() {
        return keywords;
    }

    public ArticleDTO setKeywords(List<ReferenceDTO> keywords) {
        this.keywords = keywords;
        return this;
    }
}