package com.practise.assignment.dao;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.practise.assignment.dao.entities.ArticleEntity;
import com.practise.assignment.dao.entities.AuthorEntity;
import com.practise.assignment.dao.entities.BaseEntity;
import com.practise.assignment.dao.entities.KeywordEntity;

import de.svenjacobs.loremipsum.LoremIpsum;

@Component
public class DatabaseInitializer implements CommandLineRunner {

	private static final String SYSTEM = "System";
	private static final Logger log = LoggerFactory.getLogger(DatabaseInitializer.class);
	LoremIpsum loremIpsum = new LoremIpsum();

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private KeywordRepository keywordRepository;

	@Override
	public void run(String... strings) throws Exception {
		log.info("Creating dummy data.");

		List<KeywordEntity> keywordList = createKeywords();

		keywordList = (List<KeywordEntity>) keywordRepository.saveAll(keywordList);

		List<AuthorEntity> authorsList = createAuthors();

		authorsList = (List<AuthorEntity>) authorRepository.saveAll(authorsList);

		List<ArticleEntity> articles = createArticles(10, authorsList, keywordList);

		articleRepository.saveAll(articles);

	}

	private List<KeywordEntity> createKeywords() {
		return keywords.stream().map(k -> {
			KeywordEntity keywordEntity = new KeywordEntity().setName(k);
			setAuditAttributes(keywordEntity);
			return keywordEntity;
		}).collect(Collectors.toList());
	}

	private List<AuthorEntity> createAuthors() {
		return authors.stream().map(a -> {
			AuthorEntity authorEntity = new AuthorEntity().setName(a);
			setAuditAttributes(authorEntity);
			return authorEntity;
		}).collect(Collectors.toList());

	}

	private List<ArticleEntity> createArticles(int amount, List<AuthorEntity> authors, List<KeywordEntity> keywords) {

		return Stream.generate(() -> {

			ArticleEntity articleEntity = new ArticleEntity().setHeader(loremIpsum.getParagraphs(1))
					.setShortDescription(loremIpsum.getWords(10)).setText(loremIpsum.getParagraphs(5))
					.setAuthors(pickNRandomElements(authors, random.nextInt(3)))
					.setKeywords(pickNRandomElements(keywords, random.nextInt(5))).setPublishDate(LocalDateTime.now());
			setAuditAttributes(articleEntity);
			return articleEntity;
		}).limit(amount).collect(Collectors.toList()

		);
	}

	private BaseEntity setAuditAttributes(BaseEntity baseEntity) {
		baseEntity.setCreationTime(LocalDateTime.now()).setUpdationTime(LocalDateTime.now()).setCreatedBy(SYSTEM)
				.setUpdatedBy(SYSTEM);
		return baseEntity;
	}

	private Set<KeywordEntity> getRandomKeywords(List<KeywordEntity> keywords) {
		return keywords.stream().unordered().limit(random.nextInt(keywords.size()) / 2).collect(Collectors.toSet());
	}

	private Set<AuthorEntity> getRandomAuthors(List<AuthorEntity> authors) {
		return authors.stream().unordered().limit(random.nextInt(authors.size()) / 2).collect(Collectors.toSet());
	}

	private Random random = new Random();

	private List<String> keywords = Arrays.asList("Spring Boot", "HATEOAS", "Swagger", "Architecture", "Java", "Rest",
			"MicroServices", "Elasticsearch", "Development", "Eclipse", "Germany", "World", "Politics", "Entrepreneur",
			"Technology", "Startups", "Trending", "Globalization", "Earth", "Mars");

	private List<String> authors = Arrays.asList("Gosho Aoyama", "Guillaume Apollinaire", "Maja Apostoloska",
			"Jacob M. Appel", "Max Apple", "Lisa Appignanesi", "Pawlu Aquilina", "Louis Aragon", "Jeffrey Archer",
			"Tilly Armstrong");

	public <E> Set<E> pickNRandomElements(List<E> list, int n) {
		if (n == 0) {
			n = 1;
		}
		int length = list.size();
		int startIndex = random.nextInt(length);

		return new HashSet<>(list.subList(startIndex, startIndex + n >= length ? length - 1 : startIndex + n));
	}

}
