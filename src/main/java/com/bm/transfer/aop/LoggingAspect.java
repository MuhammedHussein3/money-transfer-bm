package com.bm.transfer.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
public class LoggingAspect {

    private final Logger log = LoggerFactory.getLogger(LoggingAspect.class);


    @Before(value = "execution(* com.bm.transfer.service.serviceImpl..*(..))")
    public void beforeMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        log.info("Method Name: {}", methodName);

        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            log.info("Arg {}: {}", i, args[i]);
        }
    }
}
