package ru.sberbank.study.java.serialization;

import java.util.List;

/**
 * Created by Georgii Kolpakov on 07.12.16.
 */
public interface TestTask {
    @Cache
    long calc(int n);
    @Cache(cacheType = CacheType.FILE, keyPrefix = "cacheOfCalc", zip = true, listSize = 10_000, identityBy = {String.class, int.class})
    List<String> calc(long l, String a, int n);
    @Cache(cacheType = CacheType.FILE, identityBy = {double.class, double.class, int.class})
    String calc(double l, double l2, double l3, String a, int n);
    @Cache(identityBy = {double.class})
    String calc(double l, double l2);
}
