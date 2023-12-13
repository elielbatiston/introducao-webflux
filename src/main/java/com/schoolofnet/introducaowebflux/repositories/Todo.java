package com.schoolofnet.introducaowebflux.repositories;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "todos")
public class Todo {

	@Id
	private Long id;

	@Column(value = "name")
	private String name;

	public Todo() { }

	public Todo(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
