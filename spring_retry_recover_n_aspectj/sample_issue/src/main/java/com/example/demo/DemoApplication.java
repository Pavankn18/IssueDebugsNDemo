package com.example.demo;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

import com.example.demo.aop.LoggingAspect;

@SpringBootApplication
@Configuration
@EnableRetry
public class DemoApplication {
	
	@Autowired
	RetryableService service;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@PostConstruct
	public void postConstruct() {
		service.methodToRetry(0);
		System.out.println("Retry results " + RetryableServiceImpl.count + " \t " + LoggingAspect.count);
	}

}
