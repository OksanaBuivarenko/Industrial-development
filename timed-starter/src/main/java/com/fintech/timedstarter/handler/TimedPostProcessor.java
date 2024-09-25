package com.fintech.timedstarter.handler;


import com.fintech.timedstarter.annotation.TimedAnnotation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@Component
public class TimedPostProcessor implements BeanPostProcessor {

    private Map<String, Class> annotatedClasses = new HashMap<>();

    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        var javaClass = bean.getClass();
        if (javaClass.isAnnotationPresent(TimedAnnotation.class)) {
            annotatedClasses.put(beanName, javaClass);
        }
        return bean;
    }

    @SneakyThrows
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        Class beanClass = annotatedClasses.get(beanName);
            Method[] methods = bean.getClass().getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(TimedAnnotation.class) || annotatedClasses.containsKey(beanName)) {
                    ProxyFactory proxyFactory = new ProxyFactory(bean);
                    proxyFactory.setProxyTargetClass(true);
                    TimedInterceptor timedInterceptor = new TimedInterceptor(beanName, annotatedClasses);
                    proxyFactory.addAdvice(timedInterceptor);
                    proxyFactory.setExposeProxy(true);
                    return proxyFactory.getProxy();
                }
            }
        return bean;
    }
}