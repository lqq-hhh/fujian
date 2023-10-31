package com.test;

import java.util.Arrays;

public class QiAnXin {
    //10.7
    public int maximizeDonations (int[] donations) {
        boolean[] stores = new boolean[donations.length];
        return maximizeDonations(stores, donations, 0,0);
    }
    public int maximizeDonations(boolean[] stores, int[] donations, int index, int allNow) {
        if(index==stores.length) return allNow;
        boolean[] newStore = stores.clone();
        int result = maximizeDonations(newStore, donations, index+1, allNow);
        if(!stores[index-1<0?stores.length-1:index-1] && !stores[index+1==stores.length?0:index+1]) {
            newStore[index] = true;
            result = Math.max(result, maximizeDonations(newStore, donations, index+1, allNow+donations[index]));
        }
        return result;
    }
    public boolean CheckGameResource (int[] sequence) {
        int now = 0;
        for(int seq: sequence) {
            if(now<0) return false;
            if(seq == 0) now -= 1;
            else now += 1;
        }
        return now == 0;
    }
}
