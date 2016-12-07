package ru.sberbank.study.java.serialization.service;

import ru.sberbank.study.java.serialization.Cache;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

class MemoryCacheService extends AbstractCacheService {
    private final Map<String, Object> cacheMap = new HashMap<>();
    public boolean containsResult(Method method, Cache annotation, Object[] args) {
        return cacheMap.containsKey(getKeyFor(method, annotation, args));
    }

    public Object calculateAndSaveResult(Object object, Method method, Cache annotation, Object[] args) {
        Object result = calculateResult(object, method, args);
        cacheMap.put(getKeyFor(method, annotation, args), cutResultIfNecessary(result, annotation));
        return result;
    }

    public Object getResult(Method method, Cache annotation, Object[] args) {
        return cacheMap.get(getKeyFor(method, annotation, args));
    }
}
