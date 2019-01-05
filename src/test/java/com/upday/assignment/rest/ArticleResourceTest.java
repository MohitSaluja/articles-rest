package com.upday.assignment.rest;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practise.assignment.rest.dto.AddUpdateArticleRequest;
import com.practise.assignment.rest.dto.ArticleDTO;
import com.practise.assignment.service.ArticleService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ArticleResourceTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private ArticleService articleService;

	@Value("${test.client.username}")
	private String username;

	@Value("${test.client.password}")
	private String password;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void test_getArticles_getAll() throws Exception {
		when(articleService.getArticles(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()))
				.thenReturn(getSampleArticles());
		this.mvc.perform(get("/articles").accept(MediaType.APPLICATION_JSON).with(httpBasic(username, password)))
				.andExpect(status().isOk());
	}

	@Test
	public void test_getArticles_getByKeyword() throws Exception {
		when(articleService.getArticles(Optional.empty(), Optional.of("Java"), Optional.empty(), Optional.empty()))
				.thenReturn(getSampleArticles());

		this.mvc.perform(get("/articles").param("keyword", "Java").accept(MediaType.APPLICATION_JSON)
				.with(httpBasic(username, password))).andExpect(status().isOk());
	}

	@Test
	public void test_getArticles_getByAuthor() throws Exception {
		when(articleService.getArticles(Optional.of("Mr Author"), Optional.empty(), Optional.empty(), Optional.empty()))
				.thenReturn(getSampleArticles());

		this.mvc.perform(get("/articles").accept(MediaType.APPLICATION_JSON).with(httpBasic(username, password)))
				.andExpect(status().isOk());
	}

	@Test
	public void test_getArticles_getByPublishPeriod() throws Exception {
		when(articleService.getArticles(Optional.empty(), Optional.empty(), Optional.of(LocalDate.now()),
				Optional.of(LocalDate.now()))).thenReturn(getSampleArticles());

		this.mvc.perform(get("/articles").accept(MediaType.APPLICATION_JSON).with(httpBasic(username, password)))
				.andExpect(status().isOk());
	}

	@Test
	public void test_getArticleById() throws Exception {
		ArticleDTO article = getSampleArticleDTO();
		when(articleService.getArticle(article.getId())).thenReturn(article);

		this.mvc.perform(get("/articles/{id}", article.getId()).with(httpBasic(username, password)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id").value(article.getId().toString()))
				.andDo(print());
	}

	@Test
	public void test_createArticle() throws Exception {
		AddUpdateArticleRequest article = (AddUpdateArticleRequest) new AddUpdateArticleRequest()
				.setShortDescription("sample short description").setText("test text")
				.setPublishDate(LocalDateTime.now());
		when(articleService.save(article)).thenReturn(2L);

		final String articleJson = objectMapper.writeValueAsString(article);

		this.mvc.perform(post("/articles/").with(httpBasic(username, password)).contentType(MediaType.APPLICATION_JSON)
				.content(articleJson)).andDo(print()).andExpect(status().isCreated())
				.andExpect(header().string("location", containsString("http://localhost/articles/")));
	}

	@Test
	public void test_updateArticle() throws Exception {
		AddUpdateArticleRequest article = (AddUpdateArticleRequest) new AddUpdateArticleRequest()
				.setShortDescription("sample short description").setText("test text")
				.setPublishDate(LocalDateTime.now());
		when(articleService.update(1L, article)).thenReturn(true);
		final String articleJson = objectMapper.writeValueAsString(article);

		this.mvc.perform(put("/articles/1").with(httpBasic(username, password)).contentType(MediaType.APPLICATION_JSON)
				.content(articleJson)).andDo(print()).andExpect(status().isNoContent());
	}

	private ArticleDTO getSampleArticleDTO() {
		ArticleDTO article = new ArticleDTO().setId(1L).setShortDescription("sample short description")
				.setText("test text").setPublishDate(LocalDateTime.now());
		return article;
	}

	private List<ArticleDTO> getSampleArticles() {
		List<ArticleDTO> articles = new ArrayList<>();
		articles.add(new ArticleDTO().setId(1L).setShortDescription("sample short description").setText("test text")
				.setPublishDate(LocalDateTime.now()));
		articles.add(new ArticleDTO().setId(2L).setShortDescription("sample short description 2").setText("test text 2")
				.setPublishDate(LocalDateTime.now()));
		return articles;
	}

}