package com.example.demo.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
	public static int count = 0;
	
	@Pointcut("@annotation(com.example.demo.aop.LogAround)")
	public void logAroundPointcut() {}
	
	@Before("logAroundPointcut()")
	public void beforeLogAround() {
		count++;
	}
}
