package com.schoolofnet.introducaowebflux.repositories;

import jakarta.persistence.*;

@Entity
@Table(name = "todos")
public class Todo {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
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
