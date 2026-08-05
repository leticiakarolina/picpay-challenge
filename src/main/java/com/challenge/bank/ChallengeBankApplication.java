package com.challenge.bank;

import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class ChallengeBankApplication {

	@Value("${application.timezone}")
    private String applicationTimeZone;
	
	public static void main(String[] args) {
		SpringApplication.run(ChallengeBankApplication.class, args);
	}
	
	@PostConstruct
    public void executeAfterMain() {
        TimeZone.setDefault(TimeZone.getTimeZone(applicationTimeZone));
    }
}
