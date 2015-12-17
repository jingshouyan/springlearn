package com.jing.web.aop;

//import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
//import org.aspectj.lang.reflect.MethodSignature;

@Aspect
@Component
public class A00Log {
	@Pointcut("execution(* com.jing.web.dao..*(..))")
    private void pointcut(){} 
	
	@Before("pointcut()")
	public void before(){
		System.out.println("before ...");
	}
	
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable
    {
        System.out.println("enter around log");
        Object obj  = pjp.proceed();
//        Object[] args = pjp.getArgs();
//        MethodSignature signature = (MethodSignature) pjp.getSignature();
//        Method method = signature.getMethod();
        return obj;
    }
}
