package com.example.fit_friends;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FitFriendsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitFriendsApplication.class, args);
	}

}
