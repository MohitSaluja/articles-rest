package com.practise.assignment.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.practise.assignment.dao.entities.AuthorEntity;

public interface AuthorRepository extends CrudRepository<AuthorEntity, Long> {
	
	Optional<AuthorEntity> findByNameIgnoreCase(String name);


}
