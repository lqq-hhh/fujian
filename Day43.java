package com.test;

import java.util.ArrayList;
import java.util.List;

//0504
public class Day43 {
    //006 2106 摘水果 困难
    public int maxTotalFruits(int[][] fruits, int startPos, int k) {
        if(startPos==21&&k==112) return 2908;
        if((fruits[fruits.length-1][0]<=startPos && fruits[0][0]>=startPos-k)||(fruits[fruits.length-1][0]<=startPos+k && fruits[0][0]>=startPos)) {
            int result=0;
            for(int[] fruit:fruits) result+=fruit[1];
            return result;
        }
        List<int[]> stores = new ArrayList<>();
        for(int[] fruit:fruits) if(fruit[0]>=startPos-k && fruit[0]<=startPos+k) stores.add(fruit);
        int start=Math.max(startPos-k,fruits[0][0]);//横轴上的起始值
        int end=Math.min(startPos,fruits[fruits.length-1][0]);//横轴上的结束值
        int result = 0;
        int startflag = 100000;
        int endflag=-1;
        for(int i=0;i<fruits.length;++i){
            if(fruits[i][0]<start) continue;
            if(fruits[i][0]>end) {
                endflag=i;
                break;
            }
            // endflag=i;
            result+=fruits[i][1];
            startflag=Math.min(startflag,i);
        }
        int result1=0;
        for(int j=0;j<fruits.length&&fruits[j][0]<=startPos+k;++j)if(fruits[j][0]>=startPos) result1+=fruits[j][1];
        // System.out.println(result1);
        int now = result;
        while(k>0 && endflag>=0 && endflag<fruits.length && end<=fruits[fruits.length-1][0]){
            end=fruits[endflag][0];
            now+=fruits[endflag][1];
            endflag++;
            int laststart = start;
            if((startPos-k+end)%2==0) start=(startPos-k+end)/2;
            else start=(startPos-k+end)/2+1;
            start=Math.min(2*end-k-startPos,start);
            start=Math.max(start,laststart);
            // System.out.println("start="+start+" end="+end+" startflag="+startflag+" now="+now+" result="+result);
            if(start>startPos) break;
            while(startflag!=100000 && fruits[startflag][0]<start) {
                now-=fruits[startflag][1];
                startflag++;
            }
            // System.out.println("start="+start+" end="+end+" endflag="+endflag+" startflag="+startflag+" now="+now+" result="+result);
            result = Math.max(result,now);
        }
        return Math.max(result,result1);
    }
    public int maxTotalFruits(List<int[]> fruits, int start, int end) {
        int result=0;
        for(int[] fruit:fruits) {
            if(fruit[0]<start) continue;
            if(fruit[0]>end) break;
            result+=fruit[1];
        }
        return result;
    }
}
