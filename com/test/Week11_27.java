package com.test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

public class Week11_27 {
    //2661 找出叠图元素 中等
    public int firstCompleteIndex(int[] arr, int[][] mat) {
        HashMap<Integer, Integer> newArr = new HashMap<>();
        for (int i = 0; i < arr.length; i++) newArr.put(arr[i], i);
        int minMax = 100001;
        int[] hangMax = new int[mat[0].length];
        for (int[] ints : mat) {
            int lieMax = 0;
            for (int j = 0; j < mat[0].length; j++) {
                int nowPos = ints[j];
                lieMax = Math.max(lieMax, newArr.get(nowPos));
                hangMax[j] = Math.max(hangMax[j], newArr.get(nowPos));
            }
            minMax = Math.min(minMax, lieMax);
        }
        for (int res: hangMax) {
            minMax = Math.min(res, minMax);
        }
        return minMax;
    }
    /**
    以下是超时解法：
     主要 findPos 时间复杂度太高：
     是使用 arr 中元素去找它在 mat 中的位置 需要 O(M*N) 的时间复杂度
     改进思路：通过哈希表存储点的坐标位置
     以上这也是官方题解-24ms
     以下是3ms解法：把hashmap进行优化 优化成数组 （范围限定在了 1-m*n之间）
     */
    public int firstCompleteIndex1(int[] arr, int[][] mat) {
//        boolean[][] stores = new boolean[mat.length][mat[0].length];
        int[] hangDone = new int[mat.length];
        int[] lieDone = new int[mat[0].length];
//        HashMap<Integer, int[]> stores = new HashMap<>();
        int[] storesX = new int[arr.length+1];
        int[] storesY = new int[arr.length+1];
        for (int i = 0; i < mat.length; i++) {
            for (int j=0 ; j < mat[0].length; j++) {
//                stores.put(mat[i][j], new int[]{i,j});
                storesX[mat[i][j]] = i;
                storesY[mat[i][j]] = j;
            }
        }
//        int min = Math.min(mat.length, mat[0].length);
        for (int i=0; i<arr.length; ++i) {
//            int[] pos = findPos(arr[i], mat);
//            int[] pos = stores.get(arr[i]);
            /**
             * 下面使用的新建数组比较拖沓，去掉可以从5ms -> 3ms
             */
//            int[] pos = new int[]{storesX[arr[i]], storesY[arr[i]]};
////            stores[pos[0]][pos[1]] = true;
//            hangDone[pos[0]] += 1;
//            lieDone[pos[1]] += 1;
//            if (hangDone[pos[0]]==mat[0].length || lieDone[pos[1]] == mat.length)
//                return i;
            hangDone[storesX[arr[i]]] += 1;
            lieDone[storesY[arr[i]]] += 1;
            if (hangDone[storesX[arr[i]]]==mat[0].length || lieDone[storesY[arr[i]]] == mat.length) return i;
        }
        return 0;
    }

    public int[] findPos(int num, int[][] mat) {
        for(int m=0; m<mat.length; ++m) {
            for( int n=0; n<mat[0].length; ++n) {
                if (num == mat[m][n]) return new int[] {m,n};
            }
        }
        return null;
    }

    //1094 拼车 中等
    //呵呵 8ms 超过 6%的人呜呜
    public boolean carPooling(int[][] trips, int capacity) {
        Arrays.sort(trips, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1]-o2[1];
            }
        });
        // for (int[] trip:
        //      trips) {
        //     System.out.println(Arrays.toString(trip));
        // }
        int start = 0;
        for (int i=0; i<trips.length; ++i) {
            if (capacity < getCapNow(trips[i][1], trips, start)) return false;
            // start = trips[i][1] > trips[start][2] ? i-1 : start;
            // System.out.println("start="+start+" i="+i);
        }
        return true;
    }

    public int getCapNow(int pos, int[][] trips, int start) {
        int re = 0;
        for (;start<trips.length && pos >= trips[start][1]; ++start) {
            if (pos < trips[start][2]) re += trips[start][0];
        }
        return re;
    }

    public boolean carPooling_Fastest(int[][] trips, int capacity) {
        // 站台记
        int maxP = 0;
        // 各站台上下车记录
        for (int[] trip : trips) {
            if (trip[2] > maxP) {
                maxP = trip[2];
            }
        }
        // 站台记录，正数上车人数，负数下车人数
        int[] platforms = new int[maxP + 1];
        // 各站台上下车记录
        for (int[] trip : trips) {
            platforms[trip[1]] += trip[0];
            platforms[trip[2]] -= trip[0];
        }
        // 开车
        for (int i = 0; i <= maxP; i++) {
            capacity -= platforms[i];
            if (capacity < 0) {
                return false;
            }
        }
        return true;
    }

    //12.3
    // 1423 可获得的最大点数 中等
    // 好好好 打败100%
    public int maxScore(int[] cardPoints, int k) {
        int length = cardPoints.length;
        int sum = 0;
        for (int i=length-1; i>length-k-1; --i) sum += cardPoints[i];
        // if (k == length) return sum;
        int now = sum;
        for (int i=0; i<k; ++i) {
            now += cardPoints[i] - cardPoints[length-k+i];
            sum = Math.max(now, sum);
        }
        return sum;
    }
}