package ru.sberbank.study.java.serialization;

import org.junit.Test;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.ObjectOutputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class CacheProxyTest {
    @Test
    public void testCachingOfPrimitiveType() {
        TestTask task = CacheProxy.cache(new DifficultTask(), TestTask.class);
        long a = System.currentTimeMillis();
        long res1 = task.calc(100);
        long b = System.currentTimeMillis();
        long res2 = task.calc(100);
        long c = System.currentTimeMillis();
        assertTrue(c - b <= b- a);
        assertEquals(res1, res2);
    }

    @Test
    public void testSimpleFileCaching() {
        TestTask task = CacheProxy.cache(new DifficultTask(), TestTask.class);
        long a = System.currentTimeMillis();
        String res1 = task.calc(100.0, 5.8);
        long b = System.currentTimeMillis();
        String res2 = task.calc(100.0, 5.9);
        long c = System.currentTimeMillis();
        assertTrue(c - b <= b- a);
        assertNotEquals(res1, res2);
    }

    @Test
    public void testCachingOfLittleList() {
        TestTask task = CacheProxy.cache(new DifficultTask(), TestTask.class);
        long a = System.currentTimeMillis();
        List<String> res1 = task.calc(100L, "a", 100);
        long b = System.currentTimeMillis();
        List<String> res2 = task.calc(100L, "a", 100);
        long c = System.currentTimeMillis();
        assertTrue(c - b <= b- a);
        assertEquals(res1, res2);
    }

    @Test
    public void testCachingOfBigList() {
        TestTask task = CacheProxy.cache(new DifficultTask(), TestTask.class);
        long a = System.currentTimeMillis();
        List<String> res1 = task.calc(100L, "a", 100_000);
        long b = System.currentTimeMillis();
        List<String> res2 = task.calc(100L, "a", 100_000);
        long c = System.currentTimeMillis();
        assertTrue(c - b <= b- a);
        assertNotEquals(res1, res2);
    }
}
