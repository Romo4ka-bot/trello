package ru.kpfu.itis.trelloimpl.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Roman Leontev
 * 23:41 09.05.2021
 * group 11-905
 */

@Aspect
@Component
public class CacheAspect {

    private static final Logger logger = LoggerFactory.getLogger(CacheAspect.class);
    private Map<String, Object> cache;

    public CacheAspect() {
        cache = new HashMap<>();
    }

    @Around("@annotation(ru.kpfu.itis.trelloimpl.aspect.Cacheable)")
    public Object aroundCachedMethods(ProceedingJoinPoint thisJoinPoint)
            throws Throwable {

        StringBuilder myKey = new StringBuilder();

        myKey.append(thisJoinPoint.getTarget().getClass().getName()).append(".").append(thisJoinPoint.getSignature().getName()).append("(");

        for (final Object arg : thisJoinPoint.getArgs()) {
            myKey.append(arg.getClass().getSimpleName()).append("=").append(arg).append(";");
        }

        myKey.append(")");

        String key = myKey.toString();

        logger.debug("Key = " + key);

        Object result = cache.get(key);

        if (result == null) {
            result = thisJoinPoint.proceed();
            logger.info("Cache value - " + result);
            cache.put(key, result);
        } else {
            logger.debug("Result '" + result + "' was found in cache");
        }

        return result;
    }
}
