package com.test;

import java.util.*;
public class Week9_18 {
    static int aim;
    static int num;
    static int[] stores;
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        aim = Integer.parseInt(in.nextLine());//目标数量
        num = Integer.parseInt(in.nextLine());//商品总数
        stores = new int[num];
        for (int i = 0; i < num; ++i) stores[i] = in.nextInt();
        int result = 0;
        for (int i = 0; i < num; ++i) {
            result += find(i, stores[i]);
        }
        System.out.println(result);
    }
    public static int find(int flag, int now) {
        if (now == aim) return 1;
        if (now > aim) return 0;
        int result = 0;
        for (int i = flag; i < stores.length; ++i) {
            if (flag == i) continue;
            if (aim - now >= stores[i]) result += find(i + 1, now + stores[i]);
        }
        return result;
    }

    /**
     *  途虎笔试 9.20
     */
    //多选题
    //Q1
    public int minPathSum (int[][] grid) {
        int length = grid.length;
        int[][] stores = new int[length][length];
        for (int i = 0; i < length; ++i) {
            for (int j = 0; j < length; ++j) {
                stores[i][j] = grid[i][j];
                int add = 0;
                if (i > 0) add = stores[i - 1][j];
                if (j > 0) {
                    if (add != 0) add = Math.min(add, stores[i][j - 1]);
                    else add = stores[i][j - 1];
                }
                // if (i > 0 && j > 0) add = Math.min(add, stores[i - 1][j - 1]);
                stores[i][j] += add;
                // System.out.println(stores[i][j]);
            }
        }
        return stores[length - 1][length - 1];
    }

    //Q2
    public String carServiceRecommendation (int B, int T) {
        if(B>=200 && T>=9) return "1 1 1 1 1";
        if(B<20 || T<1) return "0 0 0 0 0";
        int[] res = new int[5];
        int[][] stores = new int[5][2];
        stores[0][0] = 20;stores[0][1]=1;
        stores[1][0] = 50;stores[1][1]=2;
        stores[2][0] = 30;stores[2][1]=1;
        stores[3][0] = 60;stores[3][1]=3;
        stores[4][0] = 40;stores[4][1]=2;
        for(int i=0;i<5;++i) {
            if(B>stores[i][0] && T<stores[i][1]) {
                res[i] = 1;
                B -= stores[i][0];
                T -= stores[i][1];
            }else {
                for(int j=0;j<i;++j) {
                    if(res[j] == 1){
                        if(stores[j][0]>stores[i][0] && B+stores[j][0]>stores[i][0] && T+stores[j][1]>stores[i][1]) {
                            res[i] = 1;
                            res[j] = 0;
                            B -= stores[i][0] - stores[j][0];
                            T -= stores[i][1] - stores[j][1];
                            break;
                        }
                    }
                }
            }
        }
        String result = "";
        for(int i=0;i<5;++i) {
            if(i != 0) result = result.concat(res[i]==0?" 0":" 1");
            else result = result.concat(res[i]==0?"0":"1");
        }
        return result;
    }

    //Q3
    List<List<Integer>> add;
    public int minFactoryStores (int[][] cities) {
        int num = cities.length;
        add = new ArrayList<List<Integer>>();
        for (int i = 0; i < num; ++i) {
            int check = -1;
            for (int k = 0; k < add.size(); ++k) {
                for (int j : add.get(k)) {
                    // System.out.println(cities[i][0]+" "+cities[i][1]+" "+cities[j][0]+" "+cities[j][1]);
                    if (cities[j][0] == cities[i][0] || cities[j][1] == cities[i][1] ||
                            cities[j][1] == cities[i][0] || cities[j][0] == cities[i][1] ) {
                        check = k;
                        break;
                    }
                }
            }
            // System.out.println(check);
            List<Integer> st = new ArrayList<>();
            st.add(i);
            if (check == -1) add.add(st);
            else add.get(check).add(i);
            // System.out.println(add);
        }
        return add.size();
    }
}
