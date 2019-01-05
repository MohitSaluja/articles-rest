package com.practise.assignment.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.practise.assignment.rest.dto.AddUpdateArticleRequest;
import com.practise.assignment.rest.dto.ArticleDTO;

public interface ArticleService {

	public Long save(AddUpdateArticleRequest request);

	public ArticleDTO getArticle(Long id);

	public void deleteArticle(Long id);

	public boolean update(Long id, AddUpdateArticleRequest request);

	public List<ArticleDTO> getArticles(Optional<String> author, Optional<String> keyword, Optional<LocalDate> fromDate,
			Optional<LocalDate> toDate);

}
