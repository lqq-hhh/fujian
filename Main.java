package com.test;

import java.text.ParseException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Day50 test = new Day50();
        System.out.println(test.summaryRanges(new int[]{0,1,2,4,5,7}));
//        Scanner in = new Scanner(System.in);
//        int numOfLine = Integer.parseInt(in.nextLine());
//        for(int i=0; i<numOfLine; ++i){
//            String[] line1 = in.nextLine().split(" ");//n, m
//            int n = Integer.parseInt(line1[0]);
//            int m = Integer.parseInt(line1[1]);
//            String[] line2 = in.nextLine().split(" ");//第一个数组
//            String[] line3 = in.nextLine().split(" ");//第二个数组
//            int[] a = new int[line2.length];
//            int[] b = new int[line2.length];
//            for(int j = 0; j<line2.length; ++j) {
//                a[j] = Integer.parseInt(line2[j]);
//                b[j] = Integer.parseInt(line3[j]);
//            }
//            Arrays.sort(a);
//            boolean flag = false;
//            int[][] stores = new int[n][2];
//            for (int j = 0; j < n; ++ j) {
//                int max = m - b[j];
//                int min = 1 - b[j];
//                boolean check = false;
//                for(int k = 0; k < line2.length; ++ k) {
//                    if(a[k] < min || a[k] > max) continue;
//                    if(!check) stores[k][0] = k;
//                    stores[k][1] = k;
//                    check = true;
//                }
//            }
//            flag = check(0, stores, new HashSet<>());
//            System.out.println(flag?"Yes":"No");
//        }
    }
    public static boolean check(int flag, int[][] stores, HashSet<Integer> used) {
        if(stores[flag].length == 0) return false;
        for(int i = 0; i < stores[flag].length; ++i) {
            if(used.contains(i)) continue;
            used.add(i);
            HashSet<Integer> now = new HashSet<>(used);
            if(check(flag+1, stores, now)) return true;
        }
        return false;
    }
}