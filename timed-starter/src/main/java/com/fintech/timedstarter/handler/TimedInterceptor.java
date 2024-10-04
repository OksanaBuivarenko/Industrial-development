package com.fintech.timedstarter.handler;

import com.fintech.timedstarter.annotation.TimedAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;
import org.aopalliance.intercept.MethodInterceptor;
import java.util.Map;

@Slf4j
public class TimedInterceptor implements MethodInterceptor {

    private final String className;

    private final Map<String, Class> annotatedClasses;

    public TimedInterceptor(String className, Map<String, Class> annotatedClasses) {
        this.className = className;
        this.annotatedClasses = annotatedClasses;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        var method = invocation.getMethod();
        if (method.isAnnotationPresent(TimedAnnotation.class) || annotatedClasses.containsKey(className)) {
            String color = "\u001B[33m";
            String colorReset = "\u001B[0m";
            log.debug(color + "Method " + method.getName() + " from class " + className + " started." + colorReset);
            var timeStart = System.nanoTime();
            var p = invocation.proceed();
            var timeFinish = System.nanoTime();
            log.debug(color + "Method " + method.getName() + " from class " + className + " finished." +
                    " Duration = " + (timeFinish - timeStart) + " ns." + colorReset);
            return p;
        }
        return invocation.proceed();
    }
}
