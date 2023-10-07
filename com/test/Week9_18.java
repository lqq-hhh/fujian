package com.test;

import com.sun.org.apache.xpath.internal.objects.XBoolean;
import com.sun.org.apache.xpath.internal.operations.Bool;

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

    /**
     * 9.23 leetcode
     */

    //1462 课程表 IV 中等
    /*
                    for (int i = 0; i < numCourses; ++i) {
                    isPre[i][ne] = isPre[i][ne] | isPre[i][cur];
                }
                关于如何使当前节点继承前节点的所有链接状态，题解使用的方法是
                不用列表维护前序节点，直接使用之前存储的节点状态
     */
    public List<Boolean> checkIfPrerequisite1(int numCourses, int[][] prerequisites, int[][] queries) {
        boolean[][] storeCheckPre;//存储是否为前提条件
        Stack<Integer> nodes;//入度变为0的节点
        List<List<Integer>> newPres;//后置节点存储
        int[] nodesRu;//记录当前节点的入度
        storeCheckPre = new boolean[numCourses][numCourses];
        nodes = new Stack<>();
        nodesRu = new int[numCourses];
        newPres = new ArrayList<>();
        for(int i=0;i<numCourses;++i) newPres.add(new ArrayList<>());
        for(int i=0;i<prerequisites.length;++i) {
            nodesRu[prerequisites[i][1]] += 1;
            newPres.get(prerequisites[i][0]).add(prerequisites[i][1]);
        }
        for(int i=0;i<numCourses;++i) if(nodesRu[i]==0) nodes.add(i);
        while (!nodes.empty()){
            int now = nodes.pop();
            for(int next:newPres.get(now)) {
                storeCheckPre[now][next] = true;
                for(int i=0;i<numCourses;++i) storeCheckPre[i][next] = storeCheckPre[i][next] | storeCheckPre[i][now];
                nodesRu[next] -= 1;
                if(nodesRu[next] == 0) {
                    nodes.add(next);
                }
            }
        }
        List<Boolean> result = new ArrayList<>();
        for(int[] qur:queries) result.add(storeCheckPre[qur[0]][qur[1]]);
        return result;
    }

    //题解2：深度优先遍历
    List<List<Integer>> storeNext;
    boolean[][] res;
    boolean[] reach;
    /**
     * 深度优先遍历思想：深度遍历，遍历的同时记录当前节点的所有前置节点
     */
    public List<Boolean> checkIfPrerequisite(int numCourses, int[][] prerequisites, int[][] queries) {
        storeNext = new ArrayList<>();
        res = new boolean[numCourses][numCourses];
        reach = new boolean[numCourses];
        for(int i=0;i<numCourses;++i) storeNext.add(new ArrayList<>());
        for(int[] pre:prerequisites) storeNext.get(pre[0]).add(pre[1]);
        for(int i=0;i<numCourses;++i) {
            checkIfPrerequisite(i, numCourses);
        }
        List<Boolean> result = new ArrayList<>();
        for(int[] que:queries) result.add(res[que[0]][que[1]]);
        return result;
    }

    public void checkIfPrerequisite(int now, int numCourses){
        if(reach[now]) return;
        reach[now] = true;
        for(int next: storeNext.get(now)) {
            checkIfPrerequisite(next,numCourses);
            res[now][next] = true;
            for(int i=0;i<numCourses;++i) res[now][i] = res[now][i] | res[next][i];
        }
    }

    //2596 检查其实巡视方案 中等
    public boolean checkValidGrid(int[][] grid) {
        int n= grid.length;
        if(n == 1) return true;
        int a = 0;
        int b = 0;
        if(grid[0][0] != 0)return false;
        boolean flag = false;
        for(int i=1;i<n*n;++i) {
            flag = false;
            int[] get = checkValidGrid(grid, i, n);
            int aNow = get[0];
            int bNow = get[1];
            if(aNow < 0 || aNow >= n) return false;
            if(bNow < 0 || bNow >= n) return false;
            if(aNow == a-1 && bNow == b-2) flag = true;
            else if(aNow == a-1 && bNow == b+2) flag = true;
            else if(aNow == a+1 && bNow == b-2) flag = true;
            else if(aNow == a+1 && bNow == b+2) flag = true;
            else if(aNow == a-2 && bNow == b-1) flag = true;
            else if(aNow == a-2 && bNow == b+1) flag = true;
            else if(aNow == a+2 && bNow == b-1) flag = true;
            else if(aNow == a+2 && bNow == b+1) flag = true;
            else if(!flag) return false;
            a = aNow;
            b = bNow;
        }
        return true;
    }

    public int[] checkValidGrid(int[][] grid, int step, int n) {
        for(int i=0;i<n;++i) {
            for(int j=0;j<n;++j) {
                if(grid[i][j] == step) {
                    return new int[]{i,j};
                }
            }
        }
        return new int[]{-1,-1};
    }

    // 1222 可以攻击国王的皇后 中等
    public List<List<Integer>> queensAttacktheKing(int[][] queens, int[] king) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> HangLeft = new ArrayList<>();
        List<Integer> HangRight = new ArrayList<>();
        List<Integer> LieUp = new ArrayList<>();
        List<Integer> LieDown = new ArrayList<>();
        List<Integer> LeftUp = new ArrayList<>();
        List<Integer> LeftDown = new ArrayList<>();
        List<Integer> RightUp = new ArrayList<>();
        List<Integer> RightDown = new ArrayList<>();
        for(int[] queen: queens) {
            List<Integer> res = new ArrayList<>();
            res.add(queen[0]);
            res.add(queen[1]);
            if(queen[0] == king[0] && queen[1]<king[1]) {
                HangLeft = checkValidQueen(HangLeft, queen, king);
            }else if(queen[1] == king[1] && queen[0]<king[0]) {
                LieUp = checkValidQueen(LieUp, queen, king);
            }else if(queen[0]+queen[1] == king[0]+king[1] && queen[0]<king[0]) {
                LeftUp = checkValidQueen(LeftUp, queen, king);
            }else if(queen[0]-queen[1] == king[0]-king[1] && queen[0]<king[0]) {
                LeftDown = checkValidQueen(LeftDown, queen, king);
            }else if(queen[0] == king[0] && queen[1]>king[1]) {
                HangRight = checkValidQueen(HangRight, queen, king);
            }else if(queen[1] == king[1] && queen[0]>king[0]) {
                LieDown = checkValidQueen(LieDown, queen, king);
            }else if(queen[0]+queen[1] == king[0]+king[1] && queen[0]>king[0]) {
                RightDown = checkValidQueen(RightDown, queen, king);
            }else if(queen[0]-queen[1] == king[0]-king[1] && queen[0]>king[0]) {
                RightUp = checkValidQueen(RightUp, queen, king);
            }
        }
        if(!HangLeft.isEmpty()) result.add(HangLeft);
        if(!HangRight.isEmpty()) result.add(HangRight);
        if(!LieUp.isEmpty()) result.add(LieUp);
        if(!LieDown.isEmpty()) result.add(LieDown);
        if(!LeftUp.isEmpty()) result.add(LeftUp);
        if(!LeftDown.isEmpty()) result.add(LeftDown);
        if(!RightUp.isEmpty()) result.add(RightUp);
        if(!RightDown.isEmpty()) result.add(RightDown);
        return result;
    }
    public List<Integer> checkValidQueen(List<Integer> before, int[] now, int[] king) {
        List<Integer> res = new ArrayList<>();
        res.add(now[0]);
        res.add(now[1]);
        if(before.isEmpty()) return res;
        int distance1 = (before.get(0)-king[0])*(before.get(0)-king[0]) + (before.get(1)-king[1])*(before.get(1)-king[1]);
        int distance2 = (now[0]-king[0])*(now[0]-king[0]) + (now[1]-king[1])*(now[1]-king[1]);
        return distance2>distance1?before:res;
        //java 乘方 Math.pow(a, n); => a^n => n个a相乘
    }
}
