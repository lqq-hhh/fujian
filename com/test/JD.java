package com.test;

import java.util.*;
public class JD {
    public static void jd1(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = Integer.parseInt(in.nextLine());//棋局数量
        for (int i = 0; i < n; i++) {
            String[] lines = new String[3];
            for(int j=0;j<3;++j) lines[j] = in.nextLine();
            boolean zi = false;
            boolean hong = false;
            for (int j = 0; j < 3; ++j) {
                for (int k = 0; k < 3; ++k) {
                    char now = lines[j].charAt(k);
                    if (now == '.') continue;
                    if (now == 'o' && check(lines, j, k)) zi = true;
                    else if (now == '*' && check(lines, j, k)) hong = true;
                }
            }
            if (zi && hong) System.out.println("draw");
            else if (zi) System.out.println("kou");
            else System.out.println("yukari");
        }
    }

    public static boolean check(String[] aim, int i, int j) {
        char now = aim[i].charAt(j);
        char up = '0';
        char down = '0';
        char left = '0';
        char right = '0';
        int num = 0;
        if (i > 0) {
            up = aim[i - 1].charAt(j);
            num += 1;
        }
        if (i < 2) {
            down = aim[i + 1].charAt(j);
            num += 1;
        }
        if (j > 0) {
            left = aim[i].charAt(j - 1);
            num += 1;
        }
        if (j < 2) {
            num += 1;
            right = aim[i].charAt(j + 1);
        }
        if (num > 2) {
            if (up == down && up != '0' && up != '.' && up != now) return true;
            if (left == right && left != '0' && left != '.' && left != now) return true;
            return false;
        }
        int k = 0;
        if (up != '0' && up != '.' && up != now) k += 1;
        if (down != '0' && down != '.' && down != now) k += 1;
        if (left != '0' && left != '.' && left != now) k += 1;
        if (right != '0' && right != '.' && right != now) k += 1;
        return k >= 2;
    }

    public static void jd2(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] nm = in.nextLine().split(" ");
        int n = Integer.parseInt(nm[0]);
        int m = Integer.parseInt(nm[1]);
        boolean[] result = new boolean[n];
        int[][] stores = new int[n][2];
        for(int i=0;i<n; ++i) {
            String[] now = in.nextLine().split(" ");
            if (now[0].equals("monster")) stores[i][0] = 1;//0-human 1-monster
            stores[i][1] = Integer.parseInt(now[1]);
        }
        for(int i=0; i<m; ++i) {
            String[] now = in.nextLine().split(" ");
            int u = Integer.parseInt(now[0]) - 1;
            int v = Integer.parseInt(now[1]) - 1;
            if(stores[u][0] == stores[v][0]) continue;
            if(result[u] || result[v]) continue;
            boolean fight = false;
            if(now[2].equals("Y")) {
                if(stores[v][0] == 0 && stores[v][1] > stores[u][1]) fight = true;
                if(stores[v][0] == 1) fight = true;
            }
            if(!fight && now[3].equals("Y")) {
                if(stores[u][0] == 0 && stores[u][1] > stores[v][1]) fight = true;
                if(stores[u][0] == 1) fight = true;
            }
            if(fight) {
                if(stores[u][1] == stores[v][1]) {
                    result[v] = true;
                    result[u] = true;
                }else {
                    result[stores[u][1] > stores[v][1]?v:u] = true;
                }
            }
        }
        for(boolean res: result) System.out.print(res?"N":"Y");
    }

    public static void jd3(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] nt = in.nextLine().split(" ");
        int n = Integer.parseInt(nt[0]);
        int t = Integer.parseInt(nt[1]);
        char[] result = new char[n];
        int[][] stores = new int[n][4];
        for (int i = 0; i < n; ++i) {
            String[] now = in.nextLine().split(" ");
            stores[i][0] = Integer.parseInt(now[0]);
            stores[i][1] = Integer.parseInt(now[1]);
            stores[i][2] = Integer.parseInt(now[2]);
            stores[i][3] = Integer.parseInt(now[3]);
        }
        result = find(stores, 0, t, 0, result);
        for (char res : result) System.out.print(res);
    }
    static int max = 0;
    public static char[] find(int[][] stores, int now, int t, int all, char[] result) {
        max = Math.max(max, all);
        if (now == result.length) {
            if(all >= max) return result;
            else return null;
        }
        result[now] = 'F';
        char[] result1 = find(stores, now+1, t, all, result);
        char[] result2 = null;
        char[] result3 = null;
        if(t >= stores[now][2]) {
            result[now] = 'B';
            result2 = find(stores, now+1, t-stores[now][2], all+stores[now][3], result);
        }
        if(t >= stores[now][0]) {
            result[now] = 'A';
            result3 = find(stores, now+1, t-stores[now][0], all+stores[now][1], result);
        }
        if(result3 != null) return result3;
        if(result2 != null) return result2;
        return result1;
    }
}
