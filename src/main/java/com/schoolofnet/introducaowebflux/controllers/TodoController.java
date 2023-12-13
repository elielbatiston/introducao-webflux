package com.schoolofnet.introducaowebflux.controllers;

import com.schoolofnet.introducaowebflux.repositories.Todo;
import com.schoolofnet.introducaowebflux.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

@RestController
@RequestMapping("todos")
public class TodoController {

	private final TodoRepository repository;

	@Qualifier("jdbcScheduler")
	private final Scheduler jdbcScheduler;

	public TodoController(
		final TodoRepository repository,
		final Scheduler jdbcScheduler
	) {
		this.repository = repository;
		this.jdbcScheduler = jdbcScheduler;
	}

	@PostMapping
	public Mono<Todo> save(@RequestBody Todo todo) {
		return this.repository.save(todo);
	}

	@GetMapping("{id}")
	@ResponseBody
	public Mono<Todo> findById(@PathVariable("id") final Long id) {
		return this.repository.findById(id);
	}

	@GetMapping
	@ResponseBody
	public Flux<Todo> findAll() {
		return this.repository.findAll();
	}

	@DeleteMapping("{id}")
	@ResponseBody
	public Mono<ResponseEntity<Void>> remove(@PathVariable("id") final Long id) {
		return this.repository.deleteById(id)
			.subscribeOn(jdbcScheduler)
			.then(Mono.fromCallable(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT)));
	}
}
