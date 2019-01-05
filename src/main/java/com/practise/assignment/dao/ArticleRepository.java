package com.practise.assignment.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.practise.assignment.dao.entities.ArticleEntity;

public interface ArticleRepository extends CrudRepository<ArticleEntity, Long> {

	List<ArticleEntity> findByPublishDateBetween(LocalDateTime start, LocalDateTime end);

}
