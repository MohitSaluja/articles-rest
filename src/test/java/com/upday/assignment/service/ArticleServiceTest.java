package com.upday.assignment.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.practise.assignment.dao.ArticleRepository;
import com.practise.assignment.dao.AuthorRepository;
import com.practise.assignment.dao.KeywordRepository;
import com.practise.assignment.dao.entities.ArticleEntity;
import com.practise.assignment.dao.entities.AuthorEntity;
import com.practise.assignment.dao.entities.KeywordEntity;
import com.practise.assignment.rest.dto.AddUpdateArticleRequest;
import com.practise.assignment.rest.dto.ArticleDTO;
import com.practise.assignment.service.impl.ArticleServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
public class ArticleServiceTest {

	@Mock
	private ArticleRepository articleRepository;

	@Mock
	private AuthorRepository authorRepository;

	@Mock
	private KeywordRepository keywordRepository;

	@InjectMocks
	private ArticleServiceImpl articleService;

	@Test
	public void test_getArticles_getAll() {
		when(articleRepository.findAll()).thenReturn(getSampleArticles());

		List<ArticleDTO> result = articleService.getArticles(Optional.empty(), Optional.empty(), Optional.empty(),
				Optional.empty());
		assertEquals(2, result.size());
	}

	@Test
	public void test_getArticles_getByKeyword() throws Exception {
		KeywordEntity keywordEntity = getSampleKeywordEntity();
		when(keywordRepository.findByNameIgnoreCase(keywordEntity.getName())).thenReturn(Optional.of(keywordEntity));

		List<ArticleDTO> result = articleService.getArticles(Optional.empty(), Optional.of(keywordEntity.getName()),
				Optional.empty(), Optional.empty());
		assertEquals(2, result.size());

	}

	@Test
	public void test_getArticles_getByAuthor() throws Exception {

		AuthorEntity authorEntity = getSampleAuthorEntity();
		when(authorRepository.findByNameIgnoreCase(authorEntity.getName())).thenReturn(Optional.of(authorEntity));

		List<ArticleDTO> result = articleService.getArticles(Optional.of(authorEntity.getName()), Optional.empty(),
				Optional.empty(), Optional.empty());
		assertEquals(2, result.size());

	}

	@Test
	public void test_getArticles_getByPublishPeriod() throws Exception {
		LocalDate today = LocalDate.now();
		LocalDateTime todayStartOfTheDay = today.atStartOfDay();

		when(articleRepository.findByPublishDateBetween(todayStartOfTheDay, todayStartOfTheDay))
				.thenReturn(getSampleArticles());

		List<ArticleDTO> result = articleService.getArticles(Optional.empty(), Optional.empty(), Optional.of(today),
				Optional.of(today));
		assertEquals(2, result.size());
	}

	@Test
	public void test_getArticleById() {

		ArticleEntity articleEntity = getSampleArticleEntity();
		when(articleRepository.findById(1L)).thenReturn(Optional.of(articleEntity));

		ArticleDTO result = articleService.getArticle(1L);

		assertSame(articleEntity.getId(), result.getId());
		assertEquals(articleEntity.getShortDescription(), result.getShortDescription());
	}

	@Test
	@WithMockUser(username = "test_user", authorities = { "ADMIN", "USER" })
	public void test_createArticle() throws Exception {
		AddUpdateArticleRequest article = (AddUpdateArticleRequest) new AddUpdateArticleRequest()
				.setShortDescription("sample short description").setText("test text")
				.setPublishDate(LocalDateTime.now());

		ArticleEntity articleEntity = (ArticleEntity) new ArticleEntity().setShortDescription("sample short description")
				.setText("test text").setPublishDate(LocalDateTime.now()).setCreatedBy("test_user")
				.setCreationTime(LocalDateTime.now()).setUpdatedBy("test_user").setUpdationTime(LocalDateTime.now());
		
		when(articleRepository.save(Mockito.any())).thenReturn(articleEntity);

		Long articleId = articleService.save(article);
		assertSame(articleEntity.getId(), articleId);

	}
	
	@Test
	@WithMockUser(username = "test_user", authorities = { "ADMIN", "USER" })
	public void test_updateArticle() throws Exception {

		AddUpdateArticleRequest article = (AddUpdateArticleRequest) new AddUpdateArticleRequest()
				.setShortDescription("sample short description").setText("test text")
				.setPublishDate(LocalDateTime.now());

		ArticleEntity articleEntity = getSampleArticleEntity();
		
		when(articleRepository.findById(articleEntity.getId())).thenReturn(Optional.of(articleEntity));
		when(articleRepository.save(Mockito.any())).thenReturn(articleEntity);

		boolean updated = articleService.update(articleEntity.getId(), article);
		assertTrue(updated);
	}
	
	private ArticleEntity getSampleArticleEntity() {
		ArticleEntity article = (ArticleEntity) new ArticleEntity().setShortDescription("sample short description")
				.setText("test text").setPublishDate(LocalDateTime.now()).setId(1L).setCreatedBy("test_user")
				.setCreationTime(LocalDateTime.now()).setUpdatedBy("test_user").setUpdationTime(LocalDateTime.now());
		return article;
	}

	private KeywordEntity getSampleKeywordEntity() {
		KeywordEntity keywordEntity = new KeywordEntity();
		keywordEntity.setName("sample keyword");
		keywordEntity.setArticles(new HashSet<>(getSampleArticles()));
		return keywordEntity;
	}

	private AuthorEntity getSampleAuthorEntity() {
		AuthorEntity keywordEntity = new AuthorEntity();
		keywordEntity.setName("sample author");
		keywordEntity.setArticles(new HashSet<>(getSampleArticles()));
		return keywordEntity;
	}

	private List<ArticleEntity> getSampleArticles() {
		List<ArticleEntity> articles = new ArrayList<>();
		articles.add((ArticleEntity) new ArticleEntity().setShortDescription("sample short description")
				.setText("test text").setPublishDate(LocalDateTime.now()).setId(1L));
		articles.add((ArticleEntity) new ArticleEntity().setShortDescription("sample short description 2")
				.setText("test text 2").setPublishDate(LocalDateTime.now()).setId(2L));
		return articles;
	}
}