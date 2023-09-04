package com.test;

import java.util.Arrays;
import java.util.*;

//0427
public class Day41 {
    //009 1048 最长字符串链
    public int longestStrChain(String[] words) {
        List<List<String>> store = new ArrayList<>();
        int max=1;
        int len = words.length;
        String[] result = new String[len];
        merge_sort_recursive(words, result, 0, len - 1);
        System.out.println(Arrays.toString(result));
        for(String i:words){
            if(store.isEmpty()) {
                List<String> list = new ArrayList<>();
                list.add(i);
                store.add(list);
                continue;
            }
            boolean flag=false;
            for(List<String> com:store){
                if(isBefore(com.get(com.size()-1),i)) {
                    com.add(i);
                    max = Math.max(max,com.size());
                    flag= true;
                    break;
                }
            }
            if(flag) {
                List<String> list = new ArrayList<>();
                list.add(i);
                store.add(list);
            }
        }
        return max;
    }
    public boolean isBefore(String a,String b){
        if(a.length()+1!=b.length()) return false;
        int flag=0;
        int i=0,j=0;
        for(;i<a.length();++i,++j){
            if(flag<2 && a.charAt(i)==b.charAt(j)) continue;
            if(flag>1) return false;
            flag++;
            ++j;
        }
        flag+=1;
        return flag==1;
    }
    static void merge_sort_recursive(String[] arr, String[] result, int start, int end) {
        if (start >= end)
            return;
        int len = end - start, mid = (len >> 1) + start;
        int start1 = start, end1 = mid;
        int start2 = mid + 1, end2 = end;
        merge_sort_recursive(arr, result, start1, end1);
        merge_sort_recursive(arr, result, start2, end2);
        int k = start;
        while (start1 <= end1 && start2 <= end2)
            result[k++] = arr[start1].length() < arr[start2].length() ? arr[start1++] : arr[start2++];
        while (start1 <= end1)
            result[k++] = arr[start1++];
        while (start2 <= end2)
            result[k++] = arr[start2++];
        for (k = start; k <= end; k++)
            arr[k] = result[k];
    }
}
