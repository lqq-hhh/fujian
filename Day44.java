package com.test;

import java.util.Stack;

//0506
public class Day44 {
    //007 1419 数青蛙 中等
    public int minNumberOfFrogs(String croakOfFrogs) {
        if(croakOfFrogs.length()%5!=0) return -1;
        Stack<Integer> c = new Stack<>();
        Stack<Integer> r = new Stack<>();
        Stack<Integer> o = new Stack<>();
        Stack<Integer> a = new Stack<>();
        int result = 0;
        int now = 0;
        for(int i=0;i<croakOfFrogs.length();++i){
            char com = croakOfFrogs.charAt(i);
            if (com == 'c') {
                c.add(i);
                now++;
            }else if(com == 'r'){
                if(c.isEmpty()) return -1;
                c.pop();
                r.add(i);
            }else if(com == 'o'){
                if(r.isEmpty()) return -1;
                r.pop();
                o.add(i);
            }else if(com == 'a'){
                if(o.isEmpty()) return -1;
                o.pop();
                a.add(i);
            }else {
                if(a.isEmpty()) return -1;
                a.pop();
                now--;
            }
            result = Math.max(result,now);
        }
        if(now!=0) return -1;
        return result;
    }
    //008 59 螺旋矩阵 中等
    public int[][] generateMatrix(int n) {
        int[][] result = new int[n][n];
        int now=1;
        int hang=0;
        int lie=-1;
        int type=1;
        while(now<=n*n){
            if(type==1){
                lie+=1;
                while(lie<n&&result[hang][lie]==0){
                    result[hang][lie]=now;
                    lie+=1;
                    now+=1;
                }
                lie-=1;
                type=2;
            }else if(type==2){
                hang+=1;
                while(hang<n&&result[hang][lie]==0){
                    result[hang][lie]=now;
                    hang+=1;
                    now+=1;
                }
                hang-=1;
                type=3;
            }else if(type==3){
                lie-=1;
                while(lie>=0&&result[hang][lie]==0){
                    result[hang][lie]=now;
                    lie-=1;
                    now+=1;
                }
                lie+=1;
                type=4;
            }else{
                hang-=1;
                while(hang>=0&&result[hang][lie]==0){
                    result[hang][lie]=now;
                    hang-=1;
                    now+=1;
                }
                hang+=1;
                type=1;
            }
        }
        return result;
    }
    //009 60 排列序列 困难
    public String getPermutation(int n, int k) {
        String result = "";
        int[] stores = new int[10];
        stores[1] = 1;
        for(int i=2;i<=n;++i) stores[i] = i*stores[i-1];
        int[] position = new int[n];
        for(int i=n-1;i>0;--i) {
        }
        return result;
    }
}
