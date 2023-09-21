package com.test;
//0522
public class Day46 {
    //001 1080 根到也路径上的不足节点 中等
    public TreeNode sufficientSubset(TreeNode root, int limit) {
        TreeNode store = new TreeNode(0);
        store.left = root;
        store = check(store,limit,0);
        // System.out.println(store);
        return store==null||store.left==null?null:store.left;
    }
    public TreeNode check(TreeNode root, int limit, int now) {
        if(root==null) return null;
        now+=root.val;
        TreeNode delete = new TreeNode(root.val);
        if(root.left!=null) {
            delete.left = check(root.left,limit,now);
        }
        if(root.right!=null){
            delete.right = check(root.right,limit,now);
        }
        if(root.left==null&&root.right==null) {
            if(now<limit) root=null;
            return root;
        }
        if(delete.left==null&&delete.right==null) return null;
        return delete;
    }
}
