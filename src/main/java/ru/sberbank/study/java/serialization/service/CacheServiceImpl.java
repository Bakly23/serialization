package ru.sberbank.study.java.serialization.service;

import ru.sberbank.study.java.serialization.Cache;
import ru.sberbank.study.java.serialization.CacheType;

import java.lang.reflect.Method;
import java.util.EnumMap;
import java.util.Map;

public class CacheServiceImpl implements CacheService {
    private final Map<CacheType, CacheService> cacheServiceMap;

    public CacheServiceImpl() {
        cacheServiceMap = new EnumMap<>(CacheType.class);
        cacheServiceMap.put(CacheType.FILE, new FileCacheService());
        cacheServiceMap.put(CacheType.IN_MEMORY, new MemoryCacheService());
    }

    public boolean containsResult(Method method, Cache annotation, Object[] args) {
        return getImplementation(annotation).containsResult(method, annotation, args);
    }

    public Object calculateAndSaveResult(Object object, Method method, Cache annotation, Object[] args) {
        return getImplementation(annotation).calculateAndSaveResult(object, method, annotation, args);
    }

    public Object getResult(Method method, Cache annotation, Object[] args) {
        return getImplementation(annotation).getResult(method, annotation, args);

    }

    private CacheService getImplementation(Cache annotation) {
        return cacheServiceMap.get(annotation.cacheType());
    }
}
