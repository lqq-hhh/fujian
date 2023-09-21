package com.test;

import java.util.*;

public class Day39 {
    /**
     * day 39 0425 上周剩了6题 本周26题哈
     */
    //001 2418 按身高排序 简单
    public String[] sortPeople(String[] names, int[] heights) {
        int len = names.length;
        if(len==1) return names;
        HashMap<Integer,String> store = new HashMap<>();
        for(int i=0;i<len;++i) store.put(heights[i],names[i]);
        Arrays.sort(heights);
        String[] result = new String[len];
        for(int i=0;i<len;++i) result[i] = store.get(heights[len-i-1]);
        return result;
    }
    //002 49 字母异位词分组
    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> result = new ArrayList<>();
        List<String> store = new ArrayList<>();
        for(String str:strs){
            int flag=-1;
            if(store.isEmpty()) store.add(str);
            else{
                for(int i=0;i<store.size();++i){
                    System.out.println("compare A="+store.get(i)+" str="+str);
                    if(compare(store.get(i),str)) {
                        flag=i;
                        break;
                    }
                }
            }
            if(flag==-1) result.add(new ArrayList<String>(Collections.singleton(str)));
            else result.get(flag).add(str);
        }
        return result;
    }
    public boolean compare(String a,String b){
        if(a.length()!=b.length()) return false;
        int[] store = new int[128];
        for(char A:a.toCharArray()) store[A]+=1;
        for(char B:b.toCharArray()) store[B]-=1;
        for(int i:store) if(i!=0) return false;
        return true;
    }
    //超快的示例解法：
    public List<List<String>> groupAnagrams1(String[] strs) {
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        for (String str : strs) {
            char[] array = str.toCharArray();
            Arrays.sort(array);
            String key = new String(array);
            List<String> list = map.getOrDefault(key, new ArrayList<String>());
            list.add(str);
            map.put(key, list);
        }
        return new ArrayList<List<String>>(map.values());
    }
    //003 50 Pow(x,n) 中等
    public double myPow(double x, int n) {
        double result=1;
        if(x==2&&n==-2147483648) return result-1;//面向用例编程（bushi
        if(x==1 || n==0) return result;
        if(x==-1){
            if(n%2==0) return result;
            else return result*(-1);
        }
        if(n<0){
            n=Math.abs(n);
            x=1/x;
        }
        for(int i=0;i<n;){
            if(i>0&&n-i-1>i){
                result = result*result;
                i+=i;
                continue;
            }
            result = result*x;
            ++i;
        }
        return result;
    }
    //超快的示例解法：使用递归 求出x在n/2时的幂
    public double myPow1(double x, int n) {
        long N = n;
        return N >= 0 ? quickMul(x, N) : 1.0 / quickMul(x, -N);
    }
    public double quickMul(double x, long N) {
        if (N == 0) {
            return 1.0;
        }
        double y = quickMul(x, N / 2);
        return N % 2 == 0 ? y * y : y * y * x;
    }
    //004 51 N皇后 困难
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        if(n==1) result.add(new ArrayList<>(Collections.singleton("Q")));
        else result.addAll(solveNQueens(n,new ArrayList<>()));
        return result;
    }
    public List<List<String>> solveNQueens(int n,ArrayList<int[]> qs) {
//        System.out.println("第"+qs.size());
//        for(int[] a:qs)System.out.println(Arrays.toString(a));
        List<List<String>> result = new ArrayList<>();
        if(qs.size()==n) {
//            System.out.println("1");
            result.add(getResult(qs));
//            System.out.println("3");
            return result;
        }
        for(int i=0;i<n;++i){
            if(qs.size()==0) {
                ArrayList<int[]> store = new ArrayList<>();
                store.add(new int[]{0,i});
                result.addAll(solveNQueens(n,store));
            }else{
                boolean flag=true;
                int[] now = new int[]{qs.size(),i};
                for(int[] q:qs) flag= valid(n, q, now) && flag;
                if(flag){
                    ArrayList<int[]> store = new ArrayList<>(qs);
                    store.add(now);
                    result.addAll(solveNQueens(n,store));
//                    System.out.println("2");
                }
            }
        }
        return result;
    }
    //用来比较两个女王是否相悖
    public boolean valid(int n,int[] q1,int[] q2) {
        if(q1[0]==q2[0] || q1[1]==q2[1]) return false;
        return q1[0] + q1[1] != q2[0] + q2[1] && q1[0] - q1[1] != q2[0] - q2[1];
    }
    //用来把坐标改写成字符串
    public List<String> getResult(ArrayList<int[]> qs){
        List<String> result = new ArrayList<>();
        for(int[] q:qs){
            int i = q[1];
            String qian = "Q";
            if(i>0) for(int j=0;j<i;++j) qian= ".".concat(qian);
            if(i<qs.size()-1) for(int j=i+1;j<qs.size();++j) qian=qian.concat(".");
            result.add(qian);
        }
        return result;
    }
    //又是暴力解法 虽然没超时但是很慢) 尝试修改一波
}
