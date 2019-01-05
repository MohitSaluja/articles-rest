package com.practise.assignment.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.practise.assignment.dao.entities.KeywordEntity;

public interface KeywordRepository extends CrudRepository<KeywordEntity, Long> {
	Optional<KeywordEntity> findByNameIgnoreCase(String name);

}
