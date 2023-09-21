package com.test;

import java.util.*;

public class Week9_4 {
    public static void main(String[] args){
        List<String> list = new ArrayList<>();
        list.add("A");
        String a = "a";
        String b = "b";
        String c = a+b;
        String d = "ab";
        System.out.println(c.equals(d));
        System.out.println(c==d);
        System.out.println(a+b==c);
        System.out.println(list.toString());
    }
    public static void xiucheng_4(String[] args) {
        Scanner in = new Scanner(System.in);
        String aim = in.nextLine();
        int result=0;
        for(int i=0;i<aim.length();++i){
            int left=0;
            int right=0;
            for(int j=i;j<aim.length();++j){
                if(aim.charAt(j) == '0') left++;
                else right++;
                if(left>right) result+=1;
            }
        }
        System.out.println(result);
    }
    public static void xiecheng_2(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] nm = in.nextLine().split(" ");
        int n = Integer.parseInt(nm[0]);
        int m = Integer.parseInt(nm[1]);
        String[] stores = new String[n];
        for(String store:stores) store = in.nextLine();
        int result = 0;
        for(int i=0; i<n; ++i){
            for(int j=0; j<m; ++j) {
                result += you(stores, new int[]{i,j});
            }
        }
        System.out.println(result);
    }

    public static int you(String[] stores, int[] point) {
        int result = 0;
        char[] aim = null;
        System.out.println(point[0]+" "+point[1]);
        if(stores[point[0]].charAt(point[1]) == 'y') aim = new char[]{'o','u'};
        else if(stores[point[0]].charAt(point[1]) == 'o') aim = new char[]{'y','u'};
        else aim = new char[]{'y','o'};
        //左上三角形
        if(point[0]>0 && point[1]>0){
            for(int i=point[0]-1; i>=0; --i) {
                int a = -1;
                a = stores[point[i]].charAt(point[1]) == aim[0]?1:a;
                a = stores[point[i]].charAt(point[1]) == aim[1]?0:a;
                if(a == -1) continue;
                for(int j=point[1]-1; j>=0;j--) {
                    if(stores[point[j]].charAt(point[0]) == aim[a]) result+=1;
                }
            }
        }
        //左下三角形
        if(point[0]<stores.length-1 && point[1]>0){
            for(int i=point[0]+1; i<stores.length; ++i) {
                int a = -1;
                a = stores[point[i]].charAt(point[1]) == aim[0]?1:a;
                a = stores[point[i]].charAt(point[1]) == aim[1]?0:a;
                if(a == -1) continue;
                for(int j=point[1]-1; j>=0;j--) {
                    if(stores[point[j]].charAt(point[0]) == aim[a]) result+=1;
                }
            }
        }
        //右上三角形
        if(point[0]>0 && point[1]<stores[0].length()-1){
            for(int i=point[0]-1; i>=0; --i) {
                int a = -1;
                a = stores[point[i]].charAt(point[1]) == aim[0]?1:a;
                a = stores[point[i]].charAt(point[1]) == aim[1]?0:a;
                if(a == -1) continue;
                for(int j=point[1]+1; j<stores[0].length();j++) {
                    if(stores[point[j]].charAt(point[0]) == aim[a]) result+=1;
                }
            }
        }
        //右下三角形
        if(point[0]<stores.length-1 && point[1]<stores[0].length()-1){
            for(int i=point[0]+1; i<stores.length; ++i) {
                int a = -1;
                a = stores[point[i]].charAt(point[1]) == aim[0]?1:a;
                a = stores[point[i]].charAt(point[1]) == aim[1]?0:a;
                if(a == -1) continue;
                for(int j=point[1]+1; j<stores[0].length();j++) {
                    if(stores[point[j]].charAt(point[0]) == aim[a]) result+=1;
                }
            }
        }
        return result;
    }

    public static void xiecheng_1(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = Integer.parseInt(in.nextLine());
        int[] stores = new int[n];
        for(int i=0;i<n;i++) stores[i] = i+1;
        List<List<Integer>> result = new ArrayList<>();
        for(int i=0; i<n ;++i) {
            List<Integer> newNow = new ArrayList<>();
            newNow.add(i+1);
            stores[i] = 0;
            result.addAll(findAll(newNow, stores, n));
            stores[i] = i+1;
        }
        System.out.println(result.size());
    }

    public static List<List<Integer>> findAll(List<Integer> now, int[] stores, int aim) {
        if(now.size() == aim) {
            System.out.println(now);
            List<List<Integer>> res = new ArrayList<>();
            if(check(now)) {
                res.add(now);
                return res;
            }
            else return null;
        }
        List<List<Integer>> result = new ArrayList<>();
        for(int i=0;i<stores.length;++i) {
            if(stores[i] == 0) continue;
            List<Integer> newNow = new ArrayList<>(now);
            newNow.add(stores[i]);
            stores[i] = 0;
            result.addAll(findAll(newNow, stores, aim));
            stores[i] = i+1;
        }
        return result;
    }

    public static boolean check(List<Integer> aim) {
        boolean result = true;
        for(int i=0; i<aim.size() - 1;++i) {
            int now = aim.get(i)+aim.get(i+1);
            if(!su(now)) return false;
        }
        return result;
    }

    public static boolean su(int aim){
        if(aim<=3) return true;
        for(int i=2; i<=Math.sqrt(aim); ++i) {
            if(aim%i == 0) return false;
        }
        return true;
    }

    //9.9 美的笔试 90 100
    public static void meidi_1(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] inputs = in.nextLine().split(" ");
        // char[] aim = inputs[0].toCharArray();
        Double dou = Double.parseDouble(inputs[1]);
        if (dou%1 != 0) {
            System.out.println("ERROR");
            return;
        }
        int length = Integer.parseInt(inputs[1]);
        char[] newAim = new char[inputs[0].length() + length];
        for (int i = 0; i < inputs[0].length() + length; ++i) {
            char nowChar = inputs[0].charAt(i);
            if(nowChar<'a' || nowChar>'z') {
                System.out.println("ERROR");
                return;
            }
            if (i < inputs[0].length()) newAim[i] = nowChar;
            else newAim[i] = '0';
        }
        for (int i = 0; i < newAim.length; i += length) {
            if (newAim[i] != '0') {
                String now = "";
                for (int j = i; j < i + length; ++j) now += newAim[j];
                if (i == 0) {
                    System.out.print(now);
                    continue;
                }
                System.out.print("," + now);
            } else break;
        }
    }
    public static void meidi_2(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] store = in.nextLine().split("");
        String a = store[0].substring(1, store[0].length() - 1);
        String b = store[1].substring(1, store[1].length() - 1);
        if (a.length() != b.length()) {
            System.out.println(false);
            return;
        }
        int[] storeA = new int[128];
        int[] storeB = new int[128];
        for (int i = 0; i < a.length(); ++i) {
            storeA[a.charAt(i)] += 1;
            storeB[b.charAt(i)] += 1;
        }
        for (int i = 0; i < storeA.length; ++i) {
            if (storeA[i] != storeB[i]) {
                System.out.println(false);
                return;
            }
        }
        System.out.println(true);
    }

    //207 课程表 中等
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> stores = new ArrayList<>();
        for(int[] pre:prerequisites) {
            List<List<Integer>> newStores = new ArrayList<>();
            for(List<Integer> store:stores) {
                if(store.get(0).equals(store.get(1))) return false;
                if(pre[0] == store.get(1)) {
                    List<Integer> newStore = new ArrayList<>();
                    newStore.add(store.get(0));
                    newStore.add(pre[1]);
                    newStores.add(newStore);
                }else if(pre[1] == store.get(0)) {
                    List<Integer> newStore = new ArrayList<>();
                    newStore.add(pre[0]);
                    newStore.add(store.get(1));
                    newStores.add(newStore);
                } else {
                    List<Integer> newStore = new ArrayList<>();
                    newStore.add(pre[0]);
                    newStore.add(pre[1]);
                    newStores.add(newStore);
                    // System.out.println(newStore);
                }
            }
            if(stores.isEmpty()) {
                List<Integer> newStore = new ArrayList<>();
                newStore.add(pre[0]);
                newStore.add(pre[1]);
                // System.out.println(newStore);
                stores.add(newStore);
            }
            stores.addAll(newStores);
        }
        for(List<Integer> store:stores) {
            System.out.println(store);
            if(store.get(0).equals(store.get(1))) return false;
        }
        return true;
    }
}
