package com.practise.assignment.rest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.practise.assignment.rest.dto.AddUpdateArticleRequest;
import com.practise.assignment.rest.dto.ArticleDTO;
import com.practise.assignment.service.ArticleService;

@RestController
@RequestMapping(value = "/articles", produces = { MediaType.APPLICATION_JSON_VALUE })
public class ArticlesResource {

	@Autowired
	private ArticleService articleService;

	@GetMapping(value = "")
	public List<ArticleDTO> getArticles(@RequestParam(name = "author", required = false) Optional<String> author,
			@RequestParam(name = "keyword", required = false) Optional<String> keyword,
			@RequestParam(name = "fromDate", required = false) @DateTimeFormat(iso = ISO.DATE) Optional<LocalDate> fromDate,
			@RequestParam(name = "toDate", required = false) @DateTimeFormat(iso = ISO.DATE) Optional<LocalDate> toDate) {
		return articleService.getArticles(author, keyword, fromDate, toDate);
	}

	@GetMapping(value = "/{id}")
	public ArticleDTO getArticle(@PathVariable("id") Long id) {
		return articleService.getArticle(id);
	}

	@PostMapping(value = "")
	public ResponseEntity<Void> createArticle(UriComponentsBuilder uriBuilder,
			@RequestBody AddUpdateArticleRequest request) {

		Long articleId = articleService.save(request);

		UriComponents uriComponents = uriBuilder.path("articles/{id}").buildAndExpand(articleId);
		final HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uriComponents.toUri());
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteArticle(@PathVariable(value = "id") Long id) {
		articleService.deleteArticle(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> updateArticle(@PathVariable(value = "id") Long id,
			@RequestBody AddUpdateArticleRequest request) {
		articleService.update(id, request);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
