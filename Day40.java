package com.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day40 {
    //005 1031 两个非重叠子数组的最大和 中等
    public int maxSumTwoNoOverlap(int[] nums, int firstLen, int secondLen) {
        if(nums.length==firstLen+secondLen) {
            int result=0;
            for(int i:nums) result+=i;
            return result;
        }
        if(firstLen<secondLen){
            int mid=firstLen;
            firstLen=secondLen;
            secondLen=mid;
        }
        int result=0;
        for(int i=0;i<=nums.length-firstLen;++i){
            int first=0;
            for(int k=i;k<i+firstLen;++k) first+=nums[k];
            if(i>=secondLen){
                int second=0;
                for(int k=0;k<secondLen;++k) second+=nums[k];
                result=Math.max(result,first+second);
                // System.out.println("first="+first+" second="+second+" result="+result+" length="+nums.length);
                for(int j=0;j<i-secondLen;++j){
                    second+=nums[j+secondLen]-nums[j];
                    result = Math.max(result,first+second);
                    // System.out.println("first="+first+" second="+second+" result="+result);
                }
            }
            if(nums.length-i-firstLen>=secondLen){
                int second=0;
                for(int k=i+firstLen;k<i+secondLen+firstLen;++k) second+=nums[k];
                result=Math.max(result,first+second);
                // System.out.println("first="+first+" second="+second+" result="+result);
                for(int j=i+firstLen;j<nums.length-secondLen;++j){
                    second+=nums[j+secondLen]-nums[j];
                    result = Math.max(result,first+second);
                    // System.out.println("first="+first+" second="+second+" result="+result);
                }
            }
        }
        return result;
    }
    //006 52 N皇后II 困难
    public int totalNQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        if(n==1) result.add(new ArrayList<>(Collections.singleton("Q")));
        else result.addAll(solveNQueens(n,new ArrayList<>()));
        return result.size();
    }
    public List<List<String>> solveNQueens(int n,ArrayList<int[]> qs) {
//        System.out.println("第"+qs.size());
//        for(int[] a:qs)System.out.println(Arrays.toString(a));
        List<List<String>> result = new ArrayList<>();
        if(qs.size()==n) {
//            System.out.println("1");
//            result.add(getResult(qs));
//            System.out.println("3");
            List<String> add = new ArrayList<>();
            add.add("1");
            result.add(add);
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
    //007 53 最大子数组和 中等
    public int maxSubArray(int[] nums) {
        int len=nums.length;
        if(len==1) return nums[0];
        int[] leftMax = new int[len];
        leftMax[0] = nums[0];
        int result = nums[0];
        for(int i=1;i<len;++i){
            leftMax[i] = Math.max(nums[i],nums[i]+leftMax[i-1]);
            result = Math.max(result,leftMax[i]);
        }
        return result;
    }
    //008 54 螺旋矩阵 中等
    //超时了可恶！原来是死循环了！
    //改好了改好了 matrix值的范围没看清楚 遇到0直接gg
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        int m=matrix.length;
        int n=matrix[0].length;
        int i=0;
        int j=0;
        result.add(matrix[0][0]);
        matrix[0][0]=101;
        int change=0;//下次改变的是哪个量
        int[][] store = {{1,1},{1,0},{0,1},{0,0}};
        while(result.size()<m*n){
            if(store[change][1]==1){
                if(store[change][0]==1) {
                    j+=1;
                    while(j<n&&matrix[i][j]!=101) {
                        result.add(matrix[i][j]);
                        matrix[i][j]=101;
                        j+=1;
                    }
                    j-=1;
                }
                else {
                    j-=1;
                    while(j>=0&&matrix[i][j]!=101) {
                        result.add(matrix[i][j]);
                        matrix[i][j]=101;
                        j-=1;
                    }
                    j+=1;
                }
            }else{
                if(store[change][0]==1) {
                    i+=1;
                    // System.out.println("j="+j+" i="+i);
                    while(i<m&&matrix[i][j]!=101) {
                        result.add(matrix[i][j]);
                        matrix[i][j]=101;
                        i+=1;
                    }
                    i-=1;
                }
                else {
                    i-=1;
                    while(i>=0&&matrix[i][j]!=101) {
                        result.add(matrix[i][j]);
                        matrix[i][j]=101;
                        i-=1;
                    }
                    i+=1;
                }
            }
            if(change<3) change+=1;
            else change=0;
        }
        return result;
    }
}
