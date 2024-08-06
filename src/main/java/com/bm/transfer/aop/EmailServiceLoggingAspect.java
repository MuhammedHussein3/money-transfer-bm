package com.bm.transfer.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class EmailServiceLoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(EmailServiceLoggingAspect.class);

    @Before("execution(* com.bm.transfer.email.SendEmailService.sendEmail(..))")
    public void beforeSendEmail(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        log.info("Method {} called with arguments: Recipient={}, Subject={}", methodName, args[0], args[2]);
    }

    @AfterThrowing(pointcut = "execution(* com.bm.transfer.email.SendEmailService.sendEmail(..))", throwing = "ex")
    public void afterThrowingSendEmail(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        log.error("Exception thrown in method {} with arguments: Recipient={}, Subject={}", methodName, args[0], args[2], ex);
    }
}