package com.test;

import java.util.*;

public class Futu {
    //Q1
    public static void main1(String[] args) {
        Scanner in = new Scanner(System.in);
        int length = in.nextInt();
        int result = 0;
        for(int i=1; i<length; ++i) {
            int now = 0;
            for (int j=1;j<=i;++j) {
                int k = i - j + 1;//区块数量
                now += k*j + k;
            }
            if(now-1 > i) break;
            result = i;
        }
        System.out.println(result);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int num = Integer.parseInt(in.nextLine());
        List<List<String>> stores = new ArrayList<>();
        for(int i=0; i<num; ++i) {
            String[] now = in.nextLine().split(" ");
            if(now[0].equals("in")) {//in操作
                List<String> thisLine = new ArrayList<>();
                thisLine.add(now[1]);
                thisLine.add(now[2]);
                stores.add(thisLine);
            }else {//out
                if(now[1].equals("0")) {
                    System.out.println(stores.get(stores.size()-1).get(1));
                    stores.remove(stores.size()-1);
                }else {
                    int k=stores.size()-1;
                    for(; k>=0; --k) if(stores.get(k).get(0).equals(now[1])) break;
                    if(k>=0) {
                        System.out.println(stores.get(k).get(1));
                        stores.remove(k);
                    }else System.out.println(-1);
                }
            }
        }
    }
}
