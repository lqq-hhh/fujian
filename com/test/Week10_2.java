package com.test;

import java.util.*;

public class Week10_2 {
    private static List<Integer> store;
    //901 股票价格跨度 中等
    public Week10_2() {
        store = new ArrayList<>();
    }

    public int next(int price) {
        store.add(price);
        int max = 1;
        for(int i=store.size()-1; i>=0; --i) {
            int now = 1;
            while (i>=0 && store.get(i)<=price) now += 1;
            max = Math.max(max, now);
        }
        return max;
    }
}
