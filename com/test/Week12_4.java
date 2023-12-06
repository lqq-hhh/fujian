package com.test;

import java.util.*;

public class Week12_4 {
    // 12.4
    // 1038 从二叉搜索树到更大和数 中等
    public TreeNode bstToGst(TreeNode root) {
        reachAll(root, 0);
        return root;
    }

    public int reachAll(TreeNode root, int val) {
        if (root == null) return val;
        val = reachAll(root.right, val);
        val += root.val;
        root.val = val;
        val = reachAll(root.left, val);
        return val;
    }

    // 12.5
    // 2477 到达首都的最少油耗 中等
    public long minimumFuelCost(int[][] roads, int seats) {
        int n = roads.length + 1;
        List<List<Integer>> storeAll = new ArrayList<>();//存储所有节点连通情况
        for (int i=0;i<n;++i) storeAll.add(new ArrayList<>());
        int[] ru = new int[n];//计算联通度
        int[] peoples = new int[n];//每个节点人数
        for (int[] r :
                roads) {
            ru[r[0]] += 1;
            ru[r[1]] += 1;
            storeAll.get(r[0]).add(r[1]);
            storeAll.get(r[1]).add(r[0]);
        }
        long result = 0L;
        HashSet<Integer> leaves = new HashSet<>();
        for (int i=0;i<n;++i) if (ru[i] == 1 && i != 0) leaves.add(i);
        while (!leaves.isEmpty()) {
            HashSet<Integer> mid = new HashSet<>();
            for (int i :
                    leaves) {
                peoples[i] += 1;
                if (peoples[i] <= seats) result += 1;
                else if(peoples[i]%seats==0) result += peoples[i]/seats;
                else result += peoples[i]/seats + 1;
                for (int a :
                        storeAll.get(i)) {
                    peoples[a] += peoples[i];
                    ru[a] -= 1;
                    if (ru[a]==1 && a != 0) mid.add(a);
                }
            }
            leaves = mid;
        }
        return result;
    }

    // 12.6
    // 2646最小化旅行的价格总和 困难 QAQ没做出来
    public int minimumTotalPriceTry(int n, int[][] edges, int[] price, int[][] trips) {
        List<List<Integer>> stores = new ArrayList<>();
        for (int i=0; i<n; ++i) stores.add(new ArrayList<>());
        int[] du = new int[n];
        for (int[] edge :
                edges) {
            stores.get(edge[0]).add(edge[1]);
            stores.get(edge[1]).add(edge[0]);
            du[edge[0]] += 1;
            du[edge[1]] += 1;
        }
        HashSet<Integer> half = new HashSet<>(); //减半节点
        half.add(0);
        int done = 1;
        while (done < n) {

        }
        int result = 0;

        return result;
    }
    public int minimumTotalPrice(int n, int[][] edges, int[] price, int[][] trips) {
        List<Integer>[] next = new List[n];
        for (int i = 0; i < n; i++) {
            next[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            next[edge[0]].add(edge[1]);
            next[edge[1]].add(edge[0]);
        }

        int[] count = new int[n];
        for (int[] trip : trips) {
            dfs(trip[0], -1, trip[1], next, count);
        }

        int[] pair = dp(0, -1, price, next, count);
        return Math.min(pair[0], pair[1]);
    }
    public boolean dfs(int node, int parent, int end, List<Integer>[] next, int[] count) {
        if (node == end) {
            count[node]++;
            return true;
        }
        for (int child : next[node]) {
            if (child == parent) {
                continue;
            }
            if (dfs(child, node, end, next, count)) {
                count[node]++;
                return true;
            }
        }
        return false;
    }

    public int[] dp(int node, int parent, int[] price, List<Integer>[] next, int[] count) {
        int[] res = {price[node] * count[node], price[node] * count[node] / 2};
        for (int child : next[node]) {
            if (child == parent) {
                continue;
            }
            int[] pair = dp(child, node, price, next, count);
            res[0] += Math.min(pair[0], pair[1]); // node 没有减半，因此可以取子树的两种情况的最小值
            res[1] += pair[0]; // node 减半，只能取子树没有减半的情况
        }
        return res;
    }
}
