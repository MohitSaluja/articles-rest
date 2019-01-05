package com.practise.assignment.rest.dto;

public class ReferenceDTO {

	private Long id;
	private String name;

	public Long getId() {
		return id;
	}

	public ReferenceDTO setId(Long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public ReferenceDTO setName(String name) {
		this.name = name;
		return this;
	}
}
