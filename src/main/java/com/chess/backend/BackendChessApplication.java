package com.chess.backend;

import com.chess.backend.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableNeo4jRepositories
public class BackendChessApplication {

	@Autowired
	RecordService recordService;

	public static void main(String[] args) {
		SpringApplication.run(BackendChessApplication.class, args);
	}

	@Bean
	public CommandLineRunner CommandLineRunnerBean() {
		return (args) -> {
			recordService.createStartNode();
		};
	}
}
