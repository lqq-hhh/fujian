package com.test;

import java.util.*;

//0530
public class Day48 {
    //001 1110 删点成林 中等
    public List<TreeNode> delNodes1(TreeNode root, int[] to_delete) {
        Arrays.sort(to_delete);
        List<TreeNode> result = new ArrayList<>();
//        root=cover(null,to_delete,root);
        return null;
    }
    public List<TreeNode> cover(TreeNode root, int[] to_delete,TreeNode now) {
        if(root==null) return null;
        List<TreeNode> result = new ArrayList<>();
        now=has(to_delete,now);
        if(now!=null){
            now.left = has(to_delete,now.left);
            now.right = has(to_delete,now.right);
        }

        return result;
    }
    public TreeNode has(int[] delete,TreeNode target) {
        if(target==null) return null;
        int left=0;
        int right=delete.length-1;
        int aim=target.val;
        if(aim<delete[left] || aim>delete[right]) return target;
        while(left<right){
            int mid=(left+right)/2;
            if(aim==delete[mid]) return null;
            else if(aim<delete[mid]) {
                right=mid-1;
            }else{
                left=mid+1;
            }
        }
        return target;
    }
    public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
        Set<Integer> toDeleteSet = new HashSet<Integer>();
        for (int val : to_delete) {
            toDeleteSet.add(val);
        }
        List<TreeNode> roots = new ArrayList<TreeNode>();
        dfs(root, true, toDeleteSet, roots);
        return roots;
    }
    public TreeNode dfs(TreeNode node, boolean isRoot, Set<Integer> toDeleteSet, List<TreeNode> roots) {
        if (node == null) {
            return null;
        }
        boolean deleted = toDeleteSet.contains(node.val);
        node.left = dfs(node.left, deleted, toDeleteSet, roots);
        node.right = dfs(node.right, deleted, toDeleteSet, roots);
        if (deleted) {
            return null;
        } else {
            if (isRoot) {
                roots.add(node);
            }
            return node;
        }
    }
    //002 2370 最长理想子序列 中等
    public int longestIdealString(String s, int k) {
        //使用的动态规划
        //dp[i]为包含第i个字母的最长递增子序列长度
        int[] dp = new int[256];
        int res = 1;
        for (char ch : s.toCharArray()) {
            int len = 0;
            for (int j = ch - k; j <= ch + k; j++) {
                // 此处不能dp[j]+1，会累加，只能找出最大值
                len = Math.max(len, dp[j]);
            }
            // 找出最大值后，再加上当前元素的1
            dp[ch] = len + 1;
            res = Math.max(dp[ch], res);
        }
        return res;
    }
}
