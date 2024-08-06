package com.bm.transfer.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class TransactionalLoggingAspect {

    @Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void transactionalMethods() {}
    @Order(0)
    @Around("transactionalMethods()")
    public Object logTransactionMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().toShortString();
        Object[] methodArgs = joinPoint.getArgs();

        log.info("Starting transaction method: {}", methodName);
        log.info("Arguments: {}", (Object) methodArgs);

        Object result;
        try {
            result = joinPoint.proceed();
            long elapsedTime = System.currentTimeMillis() - start;
            log.info("Completed transaction method: {} in {} ms", methodName, elapsedTime);
        } catch (Throwable throwable) {
            log.error("Exception in transaction method: {}", methodName, throwable);
            throw throwable;
        }

        return result;
    }
}
