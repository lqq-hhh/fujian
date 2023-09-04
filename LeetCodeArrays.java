package com.test;

import java.util.*;

public class LeetCodeArrays {
    public int minFallingPathSum0(int[][] grid) {
        int result = 0;
        int n = grid.length;//矩阵边长
        if(n == 1) return grid[0][0];
        //先从第一行中找到最小值 往下递推得到第一个解 然后再进行迭代 看是否有更小的
        int row = 0;//行
        int col = -1;//列
        for(int i = 0; i < n; ++i){
            result += i == 0 ? grid[i][getNextMinCol(-1, grid[i])] : grid[i][col] ;
            col = getNextMinCol(col, grid[i]);
        }
        //迭代 -- 需要哪些参数 逻辑是什么样的


        return result;
    }
    //获取下一行的除当前列外的最小值所在列
    public int getNextMinCol1(int exclude, int[] aim){
        int result = 0;
        if(exclude == -1){
            for(int i=0; i<aim.length; ++i) result = aim[i] < aim[result] ? i : result;
            return result;
        }
        for(int i=0; i<aim.length; ++i) {
            if(exclude == i) continue;
            result = aim[i] < aim[result] ? i : result;
        }
        return result;
    }

    /**
     *     迭代函数
     *     参数
     *     1. result作为已有标的 当迭代到大于该数时直接返回
     *     2. 数组
     *     3. 当前行
     *     4. 上一行选择的数字所在的列
     *     令状态 f[i][j]表示从数组 grid\textit{grid}grid 的前 iii 行中的每一行选择一个数字，
     *     并且第 iii 行选择的数字为 grid[i][j]grid[i][j]grid[i][j] 时，可以得到的路径和最小值。
     *     f[i][j]f[i][j]f[i][j] 可以从第 i−1i - 1i−1 行除了 f[i−1][j]f[i - 1][j]f[i−1][j] 之外的任意状态转移而来，
     *     因此有如下状态转移方程：
     *
     */
    public int minFallingPathSum(int[][] grid) {
        int n = grid.length;
        int[][] f = new int[n][n];
        for(int i = 0; i < n; ++i){//行
            for(int j = 0; j < n; ++j){//列
                f[i][j] = grid[i][j];
                if(i > 0){
                    f[i][j] += f[i - 1][getNextMinCol(j, f[i -1])];
                }
            }
        }
//         for(int i=0;i<n;++i) {
// // System.out.println(f[i][0]+" "+f[i][1]+" "+f[i][2]+" "+f[i][3]+" "+f[i][4]);
//         }
        // System.out.println(getNextMinCol(0,grid[2]));
        return f[n-1][getNextMinCol(-1, f[n-1])];
    }
    public int getNextMinCol(int exclude, int[] aim){
        int result = exclude != 0 ? 0 : 1;
        if(exclude == -1){
            for(int i=0; i<aim.length; ++i) result = aim[i] < aim[result] ? i : result;
            return result;
        }
        for(int i=0; i<aim.length; ++i) {
            if(exclude == i) continue;
            result = aim[i] < aim[result] ? i : result;
        }
        return result;
    }
}