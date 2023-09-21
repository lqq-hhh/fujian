package com.test;

import java.util.*;

//0503 哈哈一口气躺这么久真厉害
public class Day42 {
    //001 1003 检查替换后的词是否有效 中等
    public boolean isValid(String s) {
        int len = s.length();
        if(len%3 != 0) return false;
        Stack<Character> a = new Stack<>();
        for(char now:s.toCharArray()){
            if(now=='a') a.push(now);
            else if(now=='b') {
                if(a.isEmpty()||a.peek()!='a') return false;
                a.push(now);
            }
            else {
                if(a.isEmpty()) return false;
                char a1= a.pop();
                if(a1!='b' || a.isEmpty()) return false;
                a1 = a.pop();
                if(a1!='a') return false;
            }
        }
        return a.isEmpty();
    }
    //没超时但是很慢
    public boolean isValid2(String s){
        if(s.length()%3 != 0) return false;
        while(!s.isEmpty()){
            int len=s.length();
            boolean flag=false;
            for(int i=0;i<len-2;++i){
                if(s.substring(i,i+3).equals("abc")) {
                    s=s.substring(0,i).concat(s.substring(i+3));
                    flag=true;
                    break;
                }
            }
            if(!flag) return false;
        }
        return true;
    }
    //002 55 跳跃游戏 中等
    public boolean canJump(int[] nums) {
        if(nums.length==0||nums.length==1) return true;
        for(int i=0;i<nums.length-1;){
            int step = nums[i];
            if(step+i>=nums.length-1) return true;
            if(step==0) return false;
            else if(step==1) {
                i+=1;
                continue;
            }
            int max=1;
            for(int j=1;j<=step;++j) max=nums[i+j]+j>nums[i+max]+max?j:max;
            i+=max;
        }
        return true;
    }
    public boolean canJump1(int[] nums) {
        int k=0;
        for(int i=0;i<nums.length;++i){
            if(k>=nums.length-1) return true;
            if(k<i) return false;
            k=Math.max(k,i+nums[i]);
        }
        return true;
    }
    //003 56 合并区间 中等
    //改进思路：先排序，然后再进行比较合并
    public int[][] merge(int[][] intervals) {
        List<int[]> stores = new ArrayList<>();
        Arrays.sort(intervals, new Comparator<int[]>() {
            public int compare(int[] interval1, int[] interval2) {
                return interval1[0] - interval2[0];
            }
        });
        for(int[] interval:intervals){
            if(stores.isEmpty()) stores.add(interval);
            else {
                int[] store = stores.get(stores.size()-1);
                if(!(store[0]>interval[1] || store[1]<interval[0])) {
                    store[0] = Math.min(store[0],interval[0]);
                    store[1] = Math.max(store[1],interval[1]);
                }else stores.add(interval);
            }
        }
        return stores.toArray(new int[stores.size()][]);
    }
    //004 57 插入区间 中等
    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> stores = new ArrayList<>();
        boolean flag=false;
        for(int[] ints:intervals){
            if(!flag && newInterval[0]>ints[1] || newInterval[1]<ints[0]) stores.add(ints);
            else{
                if(!flag){
                    newInterval[0]=Math.min(newInterval[0],ints[0]);
                    newInterval[1]=Math.max(newInterval[1],ints[1]);
                    stores.add(newInterval);
                    flag=true;
                }else {
                    if(stores.get(stores.size()-1)[0]>ints[1] || stores.get(stores.size()-1)[1]<ints[0]) stores.add(ints);
                    else {
                        stores.get(stores.size()-1)[0] = Math.min(stores.get(stores.size()-1)[0],ints[0]);
                        stores.get(stores.size()-1)[1] = Math.max(stores.get(stores.size()-1)[1],ints[1]);
                    }
                }
            }
        }
        int[][] result;
        if(!flag) {
            stores.add(newInterval);
            result = stores.toArray(new int[stores.size()][]);
            Arrays.sort(result, new Comparator<int[]>() {
                public int compare(int[] interval1, int[] interval2) {
                    return interval1[0] - interval2[0];
                }
            });
        }
        else result = stores.toArray(new int[stores.size()][]);
        return result;
    }
    //005 58 最后一个单词的长度 简单
    public int lengthOfLastWord(String s) {
        String[] now=s.split(" ");
        return now[now.length-1].length();
    }
}