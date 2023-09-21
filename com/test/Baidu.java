package com.test;

import java.util.*;
public class Baidu {
    public static void baidu_2(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; ++i) nums[i] = in.nextInt();
        for (int i = 0; i < n - k; ++i) {
            List<Integer> flag = new ArrayList<>();
            for (int j = 0; j < k; ++j) if (nums[i + j] != j + 1) flag.add(j);
            // System.out.println(flag.size());
            if (flag.size() > 2) continue;
            if (flag.isEmpty()) {
                System.out.println("YES");
                System.out.println(0);
                return;
            }
            if (flag.size() == 2) {
                if (nums[i + flag.get(0)] == flag.get(1) + 1 &&
                        nums[i + flag.get(1)] == flag.get(0) + 1) {
                    System.out.println("YES");
                    System.out.println(1);
                    System.out.println((flag.get(0) + 1 + i) + " " + (flag.get(1) + 1 + i));
                    return;
                }
            }
            if (flag.size() == 1) {
                int res = find(i, i + k, nums, flag.get(0) + 1);
                // System.out.println(res);
                if (-1 != res) {
                    System.out.println("YES");
                    System.out.println("1");
                    System.out.println(Math.min(res+1, flag.get(0) + 1 + i) + " " + Math.max(res+1,
                            flag.get(0) + 1 + i));
                    return;
                }
            }
        }
        System.out.println("NO");
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int q = Integer.parseInt(in.nextLine());
        List<List<String>> stores = new ArrayList<>();
        while (in.hasNextLine()) {
            int num = Integer.parseInt(in.nextLine());
            String[] ops = in.nextLine().split(" ");
            int flag = ops[0].indexOf("(");
            if(-1 == flag) {//为方法声明
            }else {//为方法调用
                int end = ops[0].indexOf(")");
                boolean find = false;
                for(List<String> store:stores) {
                    if(store.get(0).equals(ops[0].substring(0,flag))) {
                        int dou = flag;
                        for(int i=0;i<store.size();++i) {
                            if(i==0) continue;
                            int newDou = ops[0].indexOf(",",dou);
                            if(newDou == -1) {

                            }
                            if(store.get(i).equals(ops[0].substring(dou,newDou))){

                            }
                        }
                    }
                }
            }
            for(int j=0;j<q;++j) System.out.println("ok.");
        }
    }
    public static int find(int start, int end, int[] nums, int aim) {
        for (int i = -1; i < start; ++i) if (i >= 0 && nums[i] == aim) return i;
        for (int i = end; i < nums.length; ++i) if (nums[i] == aim) return i;
        return -1;
    }
    public static void baidu_1(String[] args) {
        Scanner in = new Scanner(System.in);
        int num = Integer.parseInt(in.nextLine());
        while (in.hasNextInt()) {
            int n = in.nextInt();
            int m = in.nextInt();
            System.out.println((n+m)%2==0?"No":"Yes");
        }
    }
}
