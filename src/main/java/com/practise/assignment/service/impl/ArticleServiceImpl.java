package com.practise.assignment.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.practise.assignment.dao.ArticleRepository;
import com.practise.assignment.dao.AuthorRepository;
import com.practise.assignment.dao.KeywordRepository;
import com.practise.assignment.dao.entities.ArticleEntity;
import com.practise.assignment.dao.entities.AuthorEntity;
import com.practise.assignment.dao.entities.KeywordEntity;
import com.practise.assignment.exception.ResourceNotFoundException;
import com.practise.assignment.rest.dto.AddUpdateArticleRequest;
import com.practise.assignment.rest.dto.ArticleDTO;
import com.practise.assignment.rest.dto.ReferenceDTO;
import com.practise.assignment.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService {

	private static final String ARTICLE = "Article";

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private KeywordRepository keywordRepository;

	@Override
	public Long save(AddUpdateArticleRequest article) {
		ArticleEntity articleEntity = new ArticleEntity();
		mapDtoToEntity(article, articleEntity);

		addAuditAttributes(articleEntity);
		ArticleEntity articleEntityFromDB = articleRepository.save(articleEntity);
		return articleEntityFromDB.getId();
	}

	@Override
	public ArticleDTO getArticle(Long id) {
		Optional<ArticleEntity> article = articleRepository.findById(id);
		if (article.isPresent()) {
			return mapToDTO(article.get());
		} else {
			throw new ResourceNotFoundException(ARTICLE, "id", id);
		}
	}

	@Override
	public List<ArticleDTO> getArticles(Optional<String> author, Optional<String> keyword, Optional<LocalDate> fromDate,
			Optional<LocalDate> toDate) {
		if (author.isPresent()) {
			Optional<AuthorEntity> authorEntity = authorRepository.findByNameIgnoreCase(author.get());
			if (authorEntity.isPresent()) {
				return authorEntity.get().getArticles().stream().map(ArticleServiceImpl::mapToDTO)
						.collect(Collectors.toList());
			}
		} else if (keyword.isPresent()) {
			Optional<KeywordEntity> keywordEntity = keywordRepository.findByNameIgnoreCase(keyword.get());
			if (keywordEntity.isPresent()) {
				return keywordEntity.get().getArticles().stream().map(ArticleServiceImpl::mapToDTO)
						.collect(Collectors.toList());
			}
		}

		else if (fromDate.isPresent() && toDate.isPresent()) {
			return articleRepository
					.findByPublishDateBetween(fromDate.get().atStartOfDay(), toDate.get().atStartOfDay()).stream()
					.map(ArticleServiceImpl::mapToDTO).collect(Collectors.toList());
		} else {
			List<ArticleEntity> articles = (List<ArticleEntity>) articleRepository.findAll();
			return articles.stream().map(ArticleServiceImpl::mapToDTO).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	@Override
	public void deleteArticle(Long id) {
		Optional<ArticleEntity> article = articleRepository.findById(id);
		if (article.isPresent()) {
			articleRepository.deleteById(id);
		} else {
			throw new ResourceNotFoundException(ARTICLE, "id", id);
		}
	}

	@Override
	public boolean update(Long id, AddUpdateArticleRequest article) {
		ArticleEntity articleEntity = articleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ARTICLE, "id", id));
		mapDtoToEntity(article, articleEntity)
				.setUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName())
				.setUpdationTime(LocalDateTime.now());
		articleRepository.save(articleEntity);
		return true;
	}

	private static ArticleDTO mapToDTO(ArticleEntity article) {
		ArticleDTO articleDTO = new ArticleDTO();
		articleDTO.setId(article.getId());
		articleDTO.setHeader(article.getHeader());
		articleDTO.setShortDescription(article.getShortDescription());
		articleDTO.setText(article.getText());
		articleDTO.setAuthors(article.getAuthors().parallelStream()
				.map(a -> new ReferenceDTO().setId(a.getId()).setName(a.getName())).collect(Collectors.toList()));
		articleDTO.setKeywords(article.getKeywords().parallelStream()
				.map(a -> new ReferenceDTO().setId(a.getId()).setName(a.getName())).collect(Collectors.toList()));
		articleDTO.setPublishDate(article.getPublishDate());

		return articleDTO;
	}

	private ArticleEntity mapDtoToEntity(AddUpdateArticleRequest article, ArticleEntity articleEntity) {
		articleEntity.setHeader(article.getHeader());
		articleEntity.setShortDescription(article.getShortDescription());
		articleEntity.setText(article.getText());

		if (!CollectionUtils.isEmpty(article.getAuthors())) {
			List<AuthorEntity> authors = (List<AuthorEntity>) authorRepository.findAllById(article.getAuthors());
			articleEntity.setAuthors(new HashSet<>(authors));
		}
		if (!CollectionUtils.isEmpty(article.getKeywords())) {
			List<KeywordEntity> keywords = (List<KeywordEntity>) keywordRepository.findAllById(article.getKeywords());
			articleEntity.setKeywords(new HashSet<>(keywords));
		}
		articleEntity.setPublishDate(article.getPublishDate());
		return articleEntity;
	}

	private void addAuditAttributes(ArticleEntity articleEntity) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		articleEntity.setCreatedBy(username).setCreationTime(LocalDateTime.now()).setUpdatedBy(username)
				.setUpdationTime(LocalDateTime.now());
	}

}
