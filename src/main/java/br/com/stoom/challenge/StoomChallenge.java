package br.com.stoom.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class StoomChallenge {

	public static void main(String[] args) {
		SpringApplication.run(StoomChallenge.class, args);
	}
}
