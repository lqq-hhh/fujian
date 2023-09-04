package com.test;

import java.util.Arrays;

//0521
public class Day45 {
    //001 LCP 33.蓄水 简单（？真的吗
    public int storeWater(int[] bucket, int[] vat) {
        int N = vat.length;
        int[] stores = new int[N];
        int result = 0;
        int max = 0;
        int add=0;
        for(int i=0;i<N;++i){
            if(vat[i]==0) continue;
            if(bucket[i]==0) {
                add+=1;
                bucket[i]+=1;
            }
            int com=vat[i]/bucket[i];
            if(vat[i]%bucket[i]!=0) com+=1;
            int com1=vat[i]/(bucket[i]+1);
            if(vat[i]%(bucket[i]+1)!=0) com1+=1;
            if(max<com){
                if(result<com1+1) {
                    max = com1;
                    add+=1;
                    bucket[i]+=1;
                }else {
                    max = com;
                }
            }
            result=max+add;
        }
        return result;
    }
    public int storeWater1(int[] bucket, int[] vat) {
        int n = bucket.length;
        int maxk = Arrays.stream(vat).max().getAsInt();
        if (maxk == 0) {
            return 0;
        }
        int res = Integer.MAX_VALUE;
        for (int k = 1; k <= maxk && k < res; ++k) {
            int t = 0;
            for (int i = 0; i < bucket.length; ++i) {
                t += Math.max(0, (vat[i] + k - 1) / k - bucket[i]);
            }
            res = Math.min(res, t + k);
        }
        return res;
    }

}
