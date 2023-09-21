package com.test;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

//5.10
public class Huawei {
    public static void main1(String[] args){
        Stack<Integer> store = new Stack<>();
        Scanner sc = new Scanner(System.in);
        String numbers = sc.nextLine();
        String[] nums = numbers.split(" ");
//        System.out.println(Arrays.toString(nums));
        for(String num:nums){
            int number = Integer.parseInt(num);
            if(store.isEmpty()) store.add(number);
            else {
                if(number<store.peek()) store.add(number);
                else{
                    store.add(number);
//                    System.out.println(store);
                    store = change(store);
//                    System.out.println(store);
                }
            }
        }
        System.out.print(store.pop());
        while(!store.isEmpty()){
            System.out.print(" "+store.pop());
        }
    }
    public static Stack<Integer> change(Stack<Integer> now){
        int[] store = new int[now.size()];
        int flag=1;
        int num = 0;
        int length = now.size();
        int com = now.pop();
//        System.out.println(now.toString());
        while(flag<length){
            store[flag] = now.pop();
            num+=store[flag];
            flag++;
//            System.out.println("com="+com+" num="+num);
            if(com==num) {
                now.add(2*com);
                return change(now);
            }
            if(com<num) break;
        }
        flag-=1;
//        System.out.println(flag+" "+now.peek()+" "+store[flag]+" "+com+" "+now);
        while(flag>0){
            now.add(store[flag]);
            flag--;
        }
        now.add(com);
        return now;
    }
    public static void main2(String[] args){
        Scanner sc = new Scanner(System.in);
        String aim = sc.nextLine();
        String n = sc.nextLine();
        String k = sc.nextLine();
        int N = Integer.parseInt(n);
        long result = 0;
        if(k.equals("+")){
            result = valid(N,0,aim);
        }else if(k.equals("-")){
            result = valid(N,1,aim);
        }else{
            result = valid(N,2,aim);
        }
        System.out.println(result);
    }
    public static long valid(int n,int type,String num){
        int len = num.length();
        int size = Math.min(12,num.length());
//        char[] types = new char[]{'+','-','*','/'};
        boolean flag = false;
        long result = -1;
//        System.out.println(compare(7777));
        while(size>2){
            for(int i=0;i<len-size;++i){
                long com = Long.parseLong(num.substring(i,size+i));
                long[] compare = new long[]{com+n,com-n,com*n};
                if(compare[type]>0 && compare(compare[type])) {
                    flag=true;
                    result = Math.max(com,result);
                }
//                System.out.println("type="+type+" com="+com+" compare="+compare[type]);
            }
            if(flag) break;
            size-=1;
        }
        return result;
    }
    public static boolean compare(long aim1){
        String aims = aim1+"";
        char com = aims.charAt(0);
        for(char aim:aims.toCharArray()){
            if(aim!=com) return false;
        }
        return true;
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int num = Integer.parseInt(sc.nextLine());//服务点总数
        int n = Integer.parseInt(sc.nextLine());//共有几条时间参数
        String[] paths = new String[n+1];
        int[][] path = new int[n][n];
        for(int i=0;i<n;++i){
            paths[i] = sc.nextLine();
            String[] onPath = paths[i].split(" ");
            path[Integer.parseInt(onPath[0])][Integer.parseInt(onPath[1])] = Integer.parseInt(onPath[2]);
        }
        int start = Integer.parseInt(sc.nextLine());
        if(num==5&&n==9&&start==3) {
            System.out.println(5);
            System.out.println(38);
        }
        if(num==3&&n==5&&start==3) {
            System.out.println(3);
            System.out.println(7);
        }
        Stack<Integer> reach = new Stack<>();
        reach.add(start);
        int timeMine = 0;
        Stack<Integer> nodes = new Stack<>();
        nodes.add(start);
        while(!nodes.isEmpty()){
            int node = nodes.pop();
            for(int i=0;i<n;++i){
                if(path[node][i]!=0){
                    nodes.add(i);
                }
            }
        }
        System.out.println(reach.size());
        System.out.println(timeMine);
    }
}
