package ru.sberbank.study.java.serialization;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DifficultTask implements TestTask {
    public long calc(int n) {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return (long) Math.pow(1.1, n);
    }

    public List<String> calc(long l, String a, int n) {
        return IntStream.range(0, n)
                .mapToObj(i -> a + i)
                .collect(Collectors.toList());
    }

    @Override
    public String calc(double l, double l2, double l3, String a, int n) {
        return l2 + String.join("\n",
                IntStream.range(0, n)
                        .mapToObj(i -> a + l + i)
                        .collect(Collectors.toList())) + l3;
    }

    @Override
    public String calc(double l, double l2) {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Double.toString(Math.pow(l, l2));
    }
}
