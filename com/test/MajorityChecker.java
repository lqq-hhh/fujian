package com.test;

import java.util.HashMap;

public class MajorityChecker {
    private int[][][] maxAppearance;
    private int[] arr;
    public MajorityChecker(int[] arr) {
        if(arr.length == 0) maxAppearance=null;
        else {
            this.arr = arr;
            boolean flag=false;
            maxAppearance = new int[arr.length+1][2][2];
            int max=arr[0];
            HashMap<Integer,Integer> appear = new HashMap<>();
            for(int j=0;j<arr.length;++j){
                if(appear.containsKey(arr[j])) appear.replace(arr[j],appear.get(arr[j])+1);
                else appear.put(arr[j],1);
                // System.out.println("arr[]="+appear.get(arr[j])+" j="+j);
                if(max!=arr[j]){
                    if(appear.get(arr[j])>=appear.get(max)){
                        maxAppearance[j][1][0] = max;
                        maxAppearance[j][1][1] = appear.get(max);
                        max=arr[j];
                    }else{
                        maxAppearance[j][1][0] = arr[j];
                        maxAppearance[j][1][1] = appear.get(arr[j]);
                    }
                    if(!flag){
                        flag=true;
                        for(int k=j-1;k>=0;k--){
                            maxAppearance[k][1][0] = maxAppearance[k+1][1][0];
                            maxAppearance[k][1][1] = 0;
                        }
                    }
                }else{
                    if(j>0){
                        maxAppearance[j][1][0] = maxAppearance[j-1][1][0];
                        maxAppearance[j][1][1] = maxAppearance[j-1][1][1];
                    }
                }
                maxAppearance[j][0][0] = max;
                maxAppearance[j][0][1] = appear.get(max);
                // System.out.println("j="+j+" max="+maxAppearance[j][0][0]+" appearance="+maxAppearance[j][0][1]);
            }
        }

    }
    public int query(int left, int right, int threshold) {
        if(left==0) {
            if(threshold<=maxAppearance[right][0][1]){
                return maxAppearance[right][0][0];
            }else return -1;
        }
        left=left-1;
        if(maxAppearance[right][0][0]==maxAppearance[left][0][0]) {
            // System.out.println(maxAppearance[right][0][1]-maxAppearance[left][0][1]>=threshold);
            if(maxAppearance[right][0][1]-maxAppearance[left][0][1]>=threshold) return maxAppearance[right][0][0];
        }
        if(maxAppearance[right][0][0]==maxAppearance[left][1][0]) {
            // System.out.println(maxAppearance[right][0][1]-maxAppearance[left][1][1]>=threshold);
            if(maxAppearance[right][0][1]-maxAppearance[left][1][1]>=threshold) return maxAppearance[right][0][0];
        }
        if(maxAppearance[right][1][0]==maxAppearance[left][0][0]) {
            // System.out.println(maxAppearance[right][1][1]-maxAppearance[left][0][1]>=threshold);
            if(maxAppearance[right][1][1]-maxAppearance[left][0][1]>=threshold) return maxAppearance[right][1][0];
        }
        if(maxAppearance[right][1][0]==maxAppearance[left][1][0]) {
            // System.out.println(maxAppearance[right][1][1]-maxAppearance[left][1][1]>=threshold);
            if(maxAppearance[right][1][1]-maxAppearance[left][1][1]>=threshold) return maxAppearance[right][1][0];
        }
        left=left+1;
        int appearance = 0;
        int max=arr[left];
        HashMap<Integer,Integer> appear = new HashMap<>();
        for(int j=left;j<=right;++j){
            if(appear.containsKey(arr[j])) appear.replace(arr[j],appear.get(arr[j])+1);
            else appear.put(arr[j],1);
            // System.out.println(appear.get(arr[j])>=threshold);
            if(appear.get(arr[j])>=threshold) return arr[j];
            // System.out.println("arr[]="+appear.get(arr[j])+" j="+j);
            if(max!=arr[j])max=appear.get(arr[j])>appear.get(max)?arr[j]:max;
        }

        return -1;
    }
}