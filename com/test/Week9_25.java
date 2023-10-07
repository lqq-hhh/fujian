package com.test;

import java.util.*;
public class Week9_25 {
    //1333 餐厅过滤器 中等
    public List<Integer> filterRestaurants(int[][] restaurants, int veganFriendly, int maxPrice, int maxDistance) {
        List<int[]> storeRow = new ArrayList<>();
        for(int[] restaurant:restaurants) {
          if(veganFriendly == 1 && restaurant[2] == 0) continue;
          if(restaurant[3] > maxPrice) continue;
          if(restaurant[4] > maxDistance) continue;
          storeRow.add(new int[]{restaurant[0], restaurant[1]});
        }
        Collections.sort(storeRow, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if(o1[1] != o2[1]) return o2[1] - o1[1];
                else return o2[0] - o1[0];
            }
        });
        List<Integer> result = new ArrayList<>();
        for(int[] store: storeRow) result.add(store[0]);
        return result;
    }

    //LCP 50 宝石补给 简单
    public int giveGem(int[] gem, int[][] operations) {
        for(int[] op: operations) {
            int send = gem[op[0]]/2;
            gem[op[0]] -= send;
            gem[op[1]] += send;
        }
        int max = -1;
        int min = 1001;
        for(int g:gem) {
            max = Math.max(max, g);
            min = Math.min(min, g);
        }
        return max - min;
    }
}
