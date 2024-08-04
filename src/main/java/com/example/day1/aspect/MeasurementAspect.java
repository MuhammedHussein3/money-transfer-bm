package com.example.day1.aspect;

import org.apache.tomcat.util.buf.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MeasurementAspect {

    private final Logger log = LoggerFactory.getLogger(MeasurementAspect.class);

    @Around("execution(* com.example.day1.services.EmployeeService(..))")
    public Object loggedMethod(ProceedingJoinPoint joinPoint) throws Throwable {


        long startTime = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder("KPI:");
        sb.append("[").append(joinPoint.getKind()).append("]\tfor Return Type and Url: ").append(joinPoint.getSignature())
                .append("\n")
                .append("\tWithArgs: ");
        Object[] args = joinPoint.getArgs();
        String[] params = new String[args.length];
        for (int i=0;i<args.length;i++)
        {
            if (i<args.length-1)
                params[i] = args[i]+",";
            else
                params[i] = args[i]+"";
        }
        sb.append("(").append(StringUtils.join(params)).append(")");
        sb.append("\n");
        sb.append("\ttook time: ");
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        log.info(sb.append(elapsedTime).append(" ms.").toString());
        return result;
    }
}
