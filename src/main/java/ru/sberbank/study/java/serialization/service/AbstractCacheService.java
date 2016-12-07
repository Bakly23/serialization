package ru.sberbank.study.java.serialization.service;

import ru.sberbank.study.java.serialization.Cache;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Georgii Kolpakov on 07.12.16.
 */
public abstract class AbstractCacheService implements CacheService {
    protected String getKeyFor(Method method, Cache annotation, Object[] args) {
        return "cache" + File.separator + getPrefixString(method, annotation) + "("
                + getSuffixString(annotation.identityBy(), args) + ")"
                + getExtenstionString(annotation);
    }

    private String getExtenstionString(Cache annotation) {
        return annotation.zip() ? ".zip" : "";
    }

    protected Object calculateResult(Object object, Method method, Object[] args) {
        Object result = null;
        Exception exc = null;
        try {
            result = method.invoke(object, args);
        } catch (IllegalAccessException e) {
            logger.severe("Exception "+e.getMessage()+" occurred. Check settings of your java compiler.", e);
            exc = e;
        } catch (InvocationTargetException e) {
            logger.severe("Invocation of method "+method.getName()+" finished with error.", e);
            exc = e;
        } finally {
            if(exc != null) {
                throw new RuntimeException(exc);
            }
        }
        return result;
    }

    protected Object cutResultIfNecessary(Object result, Cache annotation) {
        if(result instanceof List && annotation.listSize() > -1 && ((List) result).size() > annotation.listSize()) {
            return new ArrayList(((List) result).subList(0, annotation.listSize()));
        } else {
            return result;
        }
    }

    private String getSuffixString(Class[] classes, Object[] args) {
        StringBuffer stringBuffer = new StringBuffer();
        int i = 0, j = 0;
        while(i < classes.length && j < classes.length) {
            if(classes[i].equals(args[j++].getClass())) {
                stringBuffer.append(args[j]);
                stringBuffer.append(", ");
                i++;
            }
        }
        return stringBuffer.length() > 0 ? stringBuffer.substring(0, stringBuffer.length() - 2) : stringBuffer.toString();
    }

    private String getPrefixString(Method method, Cache annotation) {
        return "".equals(annotation.keyPrefix()) ? method.getName() : annotation.keyPrefix();
    }
}
