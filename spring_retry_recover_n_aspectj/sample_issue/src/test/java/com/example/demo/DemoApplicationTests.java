package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootTest
@EnableRetry(proxyTargetClass = true)
class DemoApplicationTests {

	@SpyBean
	private RetryableService rService;
	
	@Test
	void contextLoads() {
	}
	
	//@Test
	public void testRetry() {
		rService.methodToRetry(0);
		
		
	}

}
