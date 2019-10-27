package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

import com.example.demo.aop.LoggingAspect;

@SpringBootApplication
@Configuration
@EnableRetry
public class DemoApplication {
	
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(DemoApplication.class, args);
		RetryableService service = context.getBean(RetryableService.class);
		service.methodToRetry(0);
		System.out.println("Retry results " + RetryableServiceImpl.count + " \t " + LoggingAspect.count);
	}
	

}
