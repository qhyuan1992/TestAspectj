package com.test.aspectj;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.SourceLocation;

@Aspect
public class AspectjTest {
    @Pointcut("handler(*)")
    public void throwablePointcut(){}

    @Before("throwablePointcut()")
    public void beforeThrowablePointcut(JoinPoint joinPoint) throws Throwable{
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof Throwable) {
            Throwable throwable = (Throwable) args[0];
            Log.e("beforeThrowablePointcut", "handleThrowableJoinPoint throwable:" + throwable.getMessage());
            throwable.printStackTrace();
            SourceLocation location = joinPoint.getSourceLocation();

            //throw throwable;
        }
    }
}
