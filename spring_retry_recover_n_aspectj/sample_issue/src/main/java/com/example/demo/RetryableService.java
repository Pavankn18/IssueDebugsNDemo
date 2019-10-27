package com.example.demo;

import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

public interface RetryableService {
	
	@Retryable(include = RuntimeException.class, maxAttempts = 3)
	boolean methodToRetry(int t);
	
	@Recover
	boolean recoverMethod(Exception e, int t);
}
