package ru.sberbank.study.java.serialization.service;

import com.sun.istack.internal.logging.Logger;
import ru.sberbank.study.java.serialization.Cache;

import java.lang.reflect.Method;


public interface CacheService {
    boolean containsResult(Method method, Cache annotation, Object[] args);
    Object calculateAndSaveResult(Object object, Method method, Cache annotation, Object[] args);
    Object getResult(Method method, Cache annotation, Object[] args);
    Logger logger = Logger.getLogger(CacheService.class);
}
