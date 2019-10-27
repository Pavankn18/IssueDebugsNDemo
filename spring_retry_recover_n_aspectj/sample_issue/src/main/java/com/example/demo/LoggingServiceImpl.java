package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class LoggingServiceImpl implements LoggingService{
	public static int count = 0;
	
	@Override
	public void log() {
		count++;
	}
}
