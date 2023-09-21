package com.test;

import com.sun.javafx.geom.Edge;

import java.util.*;
public class Week9_11 {
        public static int[] treeDepth;
        public static int[] nodeNum;
        public static int maxDepth;
        public static void main(String args[]) {
            Scanner in = new Scanner(System.in);
            String[] nQ = in.nextLine().split(" ");
            int n = Integer.parseInt(nQ[0]);
            int Q = Integer.parseInt(nQ[1]);
            nQ = in.nextLine().split(" ");
            int[][] routes = new int[nQ.length][2];
            for(int i=0;i<nQ.length;++i) {
                routes[i][0] = Math.min(Integer.parseInt(nQ[i]), i+1);
                routes[i][1] = Math.max(Integer.parseInt(nQ[i]), i+1);
            }
            treeDepth = new int[n];
            treeDepth[0] = 1;
            for(int  i=1;i<n;++i) {
                tree(routes,i);
            }
            System.out.println(1^6^10^1);
        }

        public static List<Integer> findWays(int aim, int[][] routes) {
            List<Integer> nodes = new ArrayList<>();
            for(int[] route:routes) {
                if(route[0] == aim) nodes.add(route[1]);
                if(route[1] == aim) nodes.add(route[0]);
            }
            return nodes;
        }


        public static void tree(int[][] routes, int now) {
            if(treeDepth[now] == 0) {
                List<Integer> nodes = findWays(now, routes);
                for(Integer node:nodes) {
                    if(treeDepth[node] == 0) tree(routes,node);
                }
                treeDepth[now] = treeDepth[nodes.get(0)] + 1;

            }
            return;
        }

    //207 课程表 中等
    int[] storesCanFinish;//储存节点状态
    List<List<Integer>> edgesCanFinish;//存储边
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        storesCanFinish = new int[numCourses];
        edgesCanFinish = new ArrayList<List<Integer>>();
        //不是遍历而是转化为可变数组进行存储边
        for(int i=0;i<numCourses;++i) edgesCanFinish.add(new ArrayList<Integer>());
        for(int[] pre:prerequisites) edgesCanFinish.get(pre[1]).add(pre[0]);
        for(int i=0;i<numCourses;++i) {
            if(storesCanFinish[i] == 0) if(!depthFind(i)) return false;
        }
        // for(int i=0;i<numCourses;i++) System.out.println(storesCanFinish[i]);
        return true;
    }
    //深度优先遍历
    public boolean depthFind(int aim) {
        if(storesCanFinish[aim] == 2) return true;
        storesCanFinish[aim] = 1;
        for(Integer edge: edgesCanFinish.get(aim)) {
            if(storesCanFinish[edge] == 1) return false;
            if(!depthFind(edge)) return false;
        }
        storesCanFinish[aim] = 2;
        return true;
    }

    //210 课程表 II 中等
    int[] storesFindOrder;
    List<List<Integer>> edgesFindOrder;
    int[] resultFindOrder;
    int index;
    Stack<Integer> stack;
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        storesFindOrder = new int[numCourses];
        edgesFindOrder = new ArrayList<List<Integer>>();
        index = numCourses-1;
        resultFindOrder = new int[numCourses];
        stack = new Stack<>();
        for(int i=0;i<numCourses;++i) edgesFindOrder.add(new ArrayList<>());
        for(int[] pre:prerequisites) edgesFindOrder.get(pre[1]).add(pre[0]);
        for(int i=0;i<numCourses;++i) {
            if(storesFindOrder[i] == 0) findOrder(i);
        }
        int[] newRes = new int[stack.size()];
        int i=0;
        while(!stack.empty()) {
            newRes[i] = stack.pop();
            i+=1;
        }
        if(index != -1) return new int[]{};
        return newRes;
    }

    public void findOrder(int aim) {
        if(storesFindOrder[aim] == 2) return;
        storesFindOrder[aim] = 1;
        for(Integer e: edgesFindOrder.get(aim)) {
            if(storesFindOrder[e] == 1) return;
            findOrder(e);
        }
        resultFindOrder[index] = aim;
        index -= 1;
        stack.push(aim);
        // System.out.println(aim);
        storesFindOrder[aim] = 2;
    }

    //630 课程表 困难
    public int scheduleCourse(int[][] courses) {
        return 0;
    }
}
