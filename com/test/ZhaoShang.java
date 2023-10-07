package com.test;

import java.util.*;

public class ZhaoShang {
    //Q1
    public int[][] getMergedIntervals (int[][] intervals) {
        for(int[] ints: intervals) {
            if(ints[0]<=ints[1]) continue;
            int mid = ints[0];
            ints[0] = ints[1];
            ints[1] = mid;
        }
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0]-o2[0];
            }
        });
        List<int[]> result = new ArrayList<>();
        for(int[] ints: intervals) {
            if(result.isEmpty()) result.add(ints);
            else {
                if(ints[0] <= result.get(result.size()-1)[1]) {
                    int[] newInt = result.get(result.size()-1);
                    result.remove(result.size()-1);
                    newInt[0] = Math.min(newInt[0], ints[0]);
                    newInt[1] = Math.max(newInt[1], ints[1]);
                    result.add(newInt);
                }
            }
        }
        int[][] res = new int[result.size()][2];
        for(int i=0;i<result.size();++i) res[i] = result.get(i);
        return res;
    }

    //Q3
    public static void main1(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] nm = in.nextLine().split(" ");
        int n = Integer.parseInt(nm[0]);
        int m = Integer.parseInt(nm[1]);
        List<List<Integer>> customers = new ArrayList<>();
        for(int i=0; i<n; ++i) {
            List<Integer> customer = new ArrayList<>();
            String[] thisLine = in.nextLine().split(" ");
            for(String aim: thisLine) {
                if(Integer.parseInt(aim) > m) break;
                customer.add(Integer.parseInt(aim));
            }
            if(customer.size() != thisLine.length) continue;
            customers.add(customer);
        }
        Collections.sort(customers, new Comparator<List<Integer>>() {
            @Override
            public int compare(List<Integer> o1, List<Integer> o2) {
                return o1.size()-o2.size();
            }
        });
        Set<Integer> store = new HashSet<>();
        int result = 0;
        for(List<Integer> cus: customers) {
            boolean flag = true;
            for(Integer c: cus) {
                if (store.contains(c)) {
                    flag = false;
                    break;
                }
            }
            if(flag) {
                result += 1;
                store.addAll(cus);
            }
        }
        System.out.println(result);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        int M = in.nextInt();
        int result = 100000;
        for(int i=0; i<N; ++i) {
            int[][] now = new int[M][2];
            result = Math.min(result, find(0,0,now, N,M));
        }
        System.out.println(result==100000?-1:result);
    }

    public static int find(int mNow, int vBefore, int[][] rh, int N, int M) {
        if(mNow == M) {
            int result = rh[M-1][0]*rh[M-1][0];
            for(int[] rhs:rh) result += rhs[0]*rhs[1];
            return result;
        }
        int result = 100000;
        for(int r = rh[mNow-1][0]+1; r <= (N-vBefore)/(rh[mNow-1][1]+1); ++r){
            for(int h = rh[mNow-1][1]+1; r*h <= N-vBefore; ++h) {
                rh[mNow][0] = r;
                rh[mNow][1] = h;
                result = Math.min(result, find(mNow+1, vBefore+r*h, rh, N,M));
            }
        }
        return result;
    }
}
