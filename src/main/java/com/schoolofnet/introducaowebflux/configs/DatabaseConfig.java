package com.schoolofnet.introducaowebflux.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Executors;

@Configuration
public class DatabaseConfig {

	@Value("${spring.datasource.maximum-pool-size}")
	private int connectionPools;

	@Bean
	public Scheduler jdbcSchedule() {
		return Schedulers.fromExecutor(Executors.newFixedThreadPool(connectionPools));
	}
}
