package com.test.runntime;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.SourceLocation;

import java.util.Set;

@Aspect
public class AspectjTest {
    @Pointcut("handler(*)")
    public void throwablePointcut(){}

    @Before("throwablePointcut()")
    public void beforeThrowablePointcut(JoinPoint joinPoint) throws Throwable{
        // whiteListMap为null就不需要插装
        if (AspectjManager.whiteListMap != null) {
            Object[] args = joinPoint.getArgs();
            if (args.length > 0 && args[0] instanceof Throwable) {
                Throwable throwable = (Throwable) args[0];
                throwable.printStackTrace();
                SourceLocation location = joinPoint.getSourceLocation();
                String fileName = location.getWithinType().getCanonicalName();
                Set<Class> whiteListClass = AspectjManager.whiteListMap.get(fileName);
                if (whiteListClass == null || !whiteListClass.contains(throwable.getClass())) {
                    throw throwable;
                }
            }
        }
    }
}
