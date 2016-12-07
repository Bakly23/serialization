package ru.sberbank.study.java.serialization;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache{
    CacheType cacheType() default CacheType.IN_MEMORY;
    String keyPrefix() default "";
    boolean zip() default false;
    int listSize() default -1;
    Class[] identityBy() default {};
}
