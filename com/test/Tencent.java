package com.test;

import java.util.*;
public class Tencent {
    public int pathNumber (TreeNode root) {
        if(root == null) return 0;
        return pathNumber(root, root.val == 1? 1 : -1);
    }
    public int pathNumber (TreeNode root, int now) {
        if(root == null) return 0;
        if(root.right == null &&root.left == null) return now == 1?1:0;
        int res = 0;
        now += root.val == 1? 1 : -1;
        res += pathNumber(root.left, now);
        res += pathNumber(root.right, now);
        return res;
    }

    public static void tencent_2(String[] args) {
        Scanner in = new Scanner(System.in);
        int all = Integer.parseInt(in.nextLine());
        while (in.hasNextLine()) { // 注意 while 处理多个 case
            int now = Integer.parseInt(in.nextLine());
            String[] a1 = in.nextLine().split(" ");
            String[] a2 = in.nextLine().split(" ");
            int[] store = new int[a1.length];
            for(int i=0;i<a1.length;++i) store[i] = Integer.parseInt(a1[i]);
            Arrays.sort(store);
            if(store.length % 2 == 0) {
                System.out.print((store[store.length/2]+store[store.length/2-1])/2);
            }else System.out.print(store[store.length/2]);
            int left = a2.length;
            while (left>0) {
                int aim = Integer.parseInt(a1[Integer.parseInt(a2[a2.length-left])]);
                int find = 0;
                while (aim != store[find]) find+=1;
                store[find] = 0;
                Arrays.sort(store);
                left--;
                int add = store.length - left;
                if(left % 2 == 0) {
                    System.out.print(" "+(store[add+store.length/2]+store[add+store.length/2-1])/2);
                }else System.out.print(" "+store[add+store.length/2]);
            }
        }
    }

    public static void tencent_3(String[] args) {
        Scanner in = new Scanner(System.in);
        int num = in.nextInt();
        int[] stores = new int[num];
        for(int i=0;i<num;++i) stores[i] = in.nextInt();
        Arrays.sort(stores);
        int left = 0;
        int right = num-1;
        int now = 0;
        boolean flag = false;
        int result = 0;
        while (left<=right){
            if(flag) {
                if(now < stores[left]) result += stores[left] - now;
                now = stores[left];
                left += 1;
            } else {
                if(now < stores[right]) result += stores[right] - now;
                now = stores[right];
                right -= 1;
            }
            flag = !flag;
        }
        left = 0;
        right = num-1;
        now = 0;
        flag = true;
        int newResult = 0;
        while (left<=right){
            if(flag) {
                if(now < stores[left]) newResult += stores[left] - now;
                now = stores[left];
                left += 1;
            } else {
                if(now < stores[right]) newResult += stores[right] - now;
                now = stores[right];
                right -= 1;
            }
            flag = !flag;
        }
        System.out.println(Math.max(newResult,result));
    }

    public static void tencent_4(String[] args) {
        Scanner in = new Scanner(System.in);
        int all = in.nextInt();
        for(int i=0;i<all;++i) {
            int n = in.nextInt();
            int k = in.nextInt();
            long res = 1L;
            if(n-k>0) {
                for(int j=1;j<n-k;++j) {
                    res *= 2;
                }
            }
            System.out.println((res - 1)%1000000007);
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int num = in.nextInt();
        int action = in.nextInt();
        int[] stores = new int[num];
        int[] flag = new int[num];
        int maxFlg = 0;
        int result = 0;
        for(int i=0;i<num;++i) {
            stores[i] = in.nextInt();
            flag[i] = find(stores[i]);
            maxFlg = flag[i] > flag[maxFlg] ? i : maxFlg;
            result += stores[i];
        }
        for(int i=0;i<action;++i) {
            if(stores[maxFlg] != 0) {
                Long minus = minus(flag[maxFlg]);
                result -= minus;
                stores[maxFlg] -= minus;
                flag[maxFlg] += 1;
                continue;
            }
            flag[maxFlg] = -1;
            for(int j=0;j<num;++j) maxFlg = flag[j]>flag[maxFlg] ? j : maxFlg;
            i -= 1;
        }
    }

    public static int find(int a){
        if(a == 0) return 0;
        int result = 0;
        while (a%2 == 0) {
            result += 1;
            a /= 2;
        }
        return result;
    }

    public static Long minus(int flag){
        long res = 1L;
        if(flag == 0) return res;
        for(int i=0;i<flag;++i) {
            res *= 2;
        }
        return res;
    }
}
