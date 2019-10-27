package com.example.demo;

import org.springframework.stereotype.Component;

import com.example.demo.aop.LogAround;

@Component
public class RetryableServiceImpl implements RetryableService {
	
	public static int count = 0;

	@Override
	@LogAround
	public boolean methodToRetry(int t) {
		count++;
		throw new RuntimeException();
	}

	@Override
	@LogAround
	public boolean recoverMethod(Exception e, int t) {
		return true;
	}

}
