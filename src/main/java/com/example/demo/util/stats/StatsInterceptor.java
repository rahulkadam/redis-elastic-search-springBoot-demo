package com.example.demo.util.stats;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class StatsInterceptor {

    @Around("@annotation(RecordTime)")
    public Object recordExecutionTime(ProceedingJoinPoint joinPoint) throws  Throwable{

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        RecordTime recordTime = signature.getMethod().getAnnotation(RecordTime.class);
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long endTime = System.currentTimeMillis() -  start;

        System.out.println(joinPoint.getSignature()+ "Method Executed in " +endTime +" ms with value " +recordTime.number());
        return proceed;
    }
}
