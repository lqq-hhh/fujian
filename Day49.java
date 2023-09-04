package com.test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;

//0603
public class Day49 {
    //1156 单字符重复子串的最大长度 中等
    public int maxRepOpt1(String text) {
        int max=0;
        int len=text.length();
        if(len==1) return 1;
        int[] num = new int[128];
        char[] stores = text.toCharArray();
        int now=1;
        num[stores[0]] = 1;
        for(int i=1;i<len;++i){
            char com = stores[i];
            if(com==stores[i-1]) now++;
            else now=1;
            num[com]++;
            max = Math.max(max,now);
        }
        if(max==len) return max;
        int left = 0;
        int right;
        while(left<len){
            char aim = stores[left];
            right = left+1;
            while(right<len && stores[right] == aim) right++;
            if(num[aim]>right-left) right++;
            while (right<len && stores[right]==aim) right++;
            int result = Math.min(right-left,num[aim]);
            max = Math.max(max,result);
            left+=1;
            while (left<len && stores[left]==stores[left-1]) left++;
        }

        return max;
    }
    //2456 不同的平均值数目 简单
    public int distinctAverages(int[] nums) {
        Arrays.sort(nums);
        int len = nums.length;
        HashSet<Double> stores = new HashSet<>();
        for(int i=0;i<len/2;++i){
            double now = (double)(nums[i]+nums[len-1-i])/2;
            stores.add(now);
        }
        return stores.size();
    }
}
