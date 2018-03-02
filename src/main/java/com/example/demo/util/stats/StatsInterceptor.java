package com.example.demo.util.stats;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Aspect
@Component
public class StatsInterceptor {

    @Around("@annotation(RecordTime)")
    public Object recordExecutionTime(ProceedingJoinPoint joinPoint) throws  Throwable{

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        RecordTime recordTime = signature.getMethod().getAnnotation(RecordTime.class);
        if(signature.getMethod().getParameterAnnotations().length > 0) {
            for(Annotation a:signature.getMethod().getParameterAnnotations()[0]) {
                if(TimeParam.class.isInstance(a)) {
                    TimeParam p = (TimeParam)a;
                    System.out.println("Filed param found : " +p.value());
                }
            }
        }
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long endTime = System.currentTimeMillis() -  start;

        System.out.println(joinPoint.getSignature()+ "Method Executed in " +endTime +" ms with value " +recordTime.number());
        return proceed;
    }
}
