package ru.kpfu.itis.trelloimpl.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author Roman Leontev
 * 17:02 09.05.2021
 * group 11-905
 */

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("within(ru.kpfu.itis.trelloimpl.service.*)")
    public void serviceMethod() {
    }

    @Before("serviceMethod()")
    public void beforeAnnotation(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] methodArgs = joinPoint.getArgs();

        log.info("Call method " + methodName + " with args " + Arrays.toString(methodArgs));
    }

    @Around("serviceMethod()")
    public Object logWebServiceCall(ProceedingJoinPoint thisJoinPoint) {

        long startTime = System.currentTimeMillis();

        Object result;
        try {
            result = thisJoinPoint.proceed();
        } catch (Throwable throwable) {
            throw new IllegalStateException(throwable);
        }

        long endTime = System.currentTimeMillis();
        log.info("Total execution time: " + (endTime - startTime) + "ms");

        return result;
    }
}