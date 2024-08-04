package com.example.day1.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

    private final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

//    @Before("execution(public * com.example.day1.services.EmployeeService.getEmployeeByEmail(String))")
//    public void beforeMethod(JoinPoint joinPoint){
//
//        String methodName = joinPoint.getSignature().toString();
//        log.info("Before method: " + methodName);
//
//        var params = joinPoint.getArgs();
//
//        int numOfParams = 0;
//        for (var arg : params) {
//            log.info(String.format("Arg %s: is %s",++numOfParams, arg));
//        }
//
//    }
//    @Before("execution(private * com.example.day1.services.EmployeeService.updateEmployee())")
//    public void beforeMethod2(JoinPoint joinPoint){
//
//        String methodName = joinPoint.getSignature().toString();
//        log.info("Before method: " + methodName);
//
//        var params = joinPoint.getArgs();
//
//        int numOfParams = 0;
//        for (var arg : params) {
//            log.info(String.format("Arg %s: is %s",++numOfParams, arg));
//        }
//
//    }
    //matches all method with any class or package
    //    @Before("within(com.example.day1.services.Employee))")
//    public void beforeMethod3(JoinPoint joinPoint){
//
//        String methodName = joinPoint.getSignature().toString();
//        log.info("Before method: " + methodName);
//
//        var params = joinPoint.getArgs();
//
//        int numOfParams = 0;
//        for (var arg : params) {
//            log.info(String.format("Arg %s: is %s",++numOfParams, arg));
//        }
//
//    }
    //matches any method in a class that has this annotation
//    @Before("@within(org.springframework.stereotype.Repository))")
//    public void beforeMethod3(JoinPoint joinPoint){
//
//        String methodName = joinPoint.getSignature().toString();
//        log.info("Before method: " + methodName);
//
//        var params = joinPoint.getArgs();
//
//        int numOfParams = 0;
//        for (var arg : params) {
//            log.info(String.format("Arg %s: is %s",++numOfParams, arg));
//        }
//
//    }

    //annotation: matches any method that is annotated with given annotation
//@Before("@annotation(org.springframework.web.bind.annotation.DeleteMapping))")
//public void beforeMethod3(JoinPoint joinPoint){
//
//    String methodName = joinPoint.getSignature().toString();
//    log.info("Before method: " + methodName);
//
//    var params = joinPoint.getArgs();
//
//    int numOfParams = 0;
//    for (var arg : params) {
//        log.info(String.format("Arg %s: is %s",++numOfParams, arg));
//    }
//
//}
    // matches any with particular arguments()
    //@Before("args(String,int))")
//    @Before("args(com.example.day1.entites.Employee))")
//    @Before("args(int)")
//    public void beforeMethod3(JoinPoint joinPoint){
//
//        String methodName = joinPoint.getSignature().toString();
//        log.info("Before method: " + methodName);
//
//        var params = joinPoint.getArgs();
//
//        int numOfParams = 0;
//        for (var arg : params) {
//            log.info(String.format("Arg %s: is %s",++numOfParams, arg));
//        }
//
//    }

    //matches any method with particular arguments and thar parameters class annotated with
    //particular annotation
//    @Before("@args(org.springframework.stereotype.Service)")
//    public void beforeMethod(JoinPoint joinPoint){
//
//        String methodName = joinPoint.getSignature().toString();
//        log.info("Before method: " + methodName);
//
//        var params = joinPoint.getArgs();
//
//        int numOfParams = 0;
//        for (var arg : params) {
//            log.info(String.format("Arg %s: is %s",++numOfParams, arg));
//        }
//
//    }
    //matches any method on a particular instance
//    @Before("target(com.example.day1.mapper.EmployeeMapper)")
//    public void beforeMethod(JoinPoint joinPoint){
//
//        String methodName = joinPoint.getSignature().toString();
//        log.info("Before method: " + methodName);
//
//        var params = joinPoint.getArgs();
//
//        int numOfParams = 0;
//        for (var arg : params) {
//            log.info(String.format("Arg %s: is %s",++numOfParams, arg));
//        }
//
//    }


    @Before("execution(public com.example.day1.entites.Employee com.example.day1.services.EmployeeService.getEmployeeByEmail(..))"
    +"|| @within(org.springframework.web.bind.annotation.RestController)")
    public void beforeMethod(JoinPoint joinPoint){

        String methodName = joinPoint.getSignature().toString();
        log.info("Before method: " + methodName);

        var params = joinPoint.getArgs();

        int numOfParams = 0;
        for (var arg : params) {
            log.info(String.format("Arg %s: is %s",++numOfParams, arg));
        }

    }
}
