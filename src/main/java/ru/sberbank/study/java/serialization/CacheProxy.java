package ru.sberbank.study.java.serialization;

import ru.sberbank.study.java.serialization.service.CacheService;
import ru.sberbank.study.java.serialization.service.CacheServiceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class CacheProxy {
    public static <T> T cache(T classToCache, Class<? super T> clazz) {
        if (classToCache.getClass().getInterfaces().length == 0) {
            throw new RuntimeException("Your class must implement some interface");
        }
        if (!Arrays.asList(classToCache.getClass().getInterfaces()).contains(clazz)) {
            throw new RuntimeException("You must pass interface that your class implements as second argument.");
        }
        return (T) Proxy.newProxyInstance(classToCache.getClass().getClassLoader(),
                new Class[]{clazz},
                new InvocationHandler() {
                    private final CacheService service = new CacheServiceImpl();

                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Cache annotation = method.getAnnotation(Cache.class);
                        if (annotation != null) {
                            if (service.containsResult(method, annotation, args)) {
                                return service.getResult(method, annotation, args);
                            } else {
                                return service.calculateAndSaveResult(classToCache, method, annotation, args);
                            }
                        }
                        return method.invoke(classToCache, args);
                    }
                });
    }
}
