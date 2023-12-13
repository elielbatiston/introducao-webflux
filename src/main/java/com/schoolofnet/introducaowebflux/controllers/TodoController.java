package com.schoolofnet.introducaowebflux.controllers;

import com.schoolofnet.introducaowebflux.repositories.Todo;
import com.schoolofnet.introducaowebflux.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

@RestController
@RequestMapping("todos")
public class TodoController {

	private final TodoRepository repository;

	private final TransactionTemplate transactionTemplate;

	@Qualifier("jdbcScheduler")
	private final Scheduler jdbcScheduler;

	public TodoController(
		final TodoRepository repository,
		final TransactionTemplate transactionTemplate,
		final Scheduler jdbcScheduler
	) {
		this.repository = repository;
		this.transactionTemplate = transactionTemplate;
		this.jdbcScheduler = jdbcScheduler;
	}

	@PostMapping
	public Mono<Todo> save(@RequestBody Todo todo) {
		final Mono<Todo> op = Mono.fromCallable(
			() -> this.transactionTemplate.execute(action -> this.repository.save(todo))
		);

		return op;
	}

	@GetMapping("{id}")
	@ResponseBody
	public Mono<Todo> findById(@PathVariable("id") final Long id) {
		return Mono.justOrEmpty(this.repository.findById(id));
	}

	@GetMapping
	@ResponseBody
	public Flux<Todo> findAll() {
		return Flux.defer(() -> Flux.fromIterable(this.repository.findAll())).subscribeOn(jdbcScheduler);
	}

	@DeleteMapping("{id}")
	@ResponseBody
	public Mono<ResponseEntity<Void>> remove(@PathVariable("id") final Long id) {
		return Mono.fromCallable(
			() -> this.transactionTemplate.execute(action -> {
				this.repository.deleteById(id);
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			})
		).subscribeOn(jdbcScheduler);
	}
}
