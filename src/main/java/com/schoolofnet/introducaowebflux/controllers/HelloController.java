package com.schoolofnet.introducaowebflux.controllers;

import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HelloController {

	@GetMapping
	@ResponseBody
	public Publisher<String> sayHello() {
		return Mono.just("Hello Worl from Spring Webflux by School of net");
	}
}
