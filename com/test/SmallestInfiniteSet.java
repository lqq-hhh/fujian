package com.test;

import java.util.*;

class SmallestInfiniteSet {
    int minNow;
    List<Integer> linkList;
    HashSet<Integer> set;
    public SmallestInfiniteSet() {
        minNow = 1;
        linkList = new LinkedList<>();
        set = new HashSet<>();
    }

    public int popSmallest() {
        int re = minNow;
        set.add(re);
//        addNum(re);
        minNow = findNext(re+1);
        return re;
    }

    public void addBack(int num) {
        if(!set.contains(num)) return;
        minNow = Math.min(minNow, num);
        set.remove(num);
    }

    public int findNext(int num) {
        while(set.contains(num)) num += 1;
        return num;
    }

    //以下是使用链表的解法，感觉不咋行，会很慢
    public void addNum(int num) {
        if(linkList.isEmpty()) {
            linkList.add(num);
            return;
        }
        for(int i=0; i<linkList.size(); ++i) {
            if (linkList.get(i)<num) continue;
            if (linkList.get(i) == num) return;
            if (linkList.get(i) > num) linkList.add(i,num);
            return;
        }
    }

    public void deleteNum(int num) {
        for (int i=0; i<linkList.size(); ++i) {
            if (num == linkList.get(i)) {
                linkList.remove(i);
                minNow = Math.min(minNow, num);
                return;
            }
        }
    }
}
