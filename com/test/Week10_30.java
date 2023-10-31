package com.test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class Week10_30 {
    // 380 O(1)时间插入删除获取元素 中等
    class RandomizedSet {

        HashSet<Integer> stores;
        public RandomizedSet() {
            stores = new HashSet<>();
        }

        public boolean insert(int val) {
            if(stores.contains(val)) return false;
            stores.add(val);
            return true;
        }

        public boolean remove(int val) {
            if(!stores.contains(val)) return false;
            stores.remove(val);
            return true;
        }

        public int getRandom() {
            Random numList = new Random();
            int num =  numList.nextInt(stores.size());
            int now = 0;
            for(int val: stores){
                if(num == now) return val;
                now += 1;
            }
            return 0;
        }
    }

    //以下是官方解法

    /**
     * 通过key-value key存储数字 value存储相应index 利用数组存储所有可能值
     */
    class RandomizedSet1 {

        private int idx = -1;
        static int[] nums = new int[100010];
        Random random = new Random();
        private HashMap<Integer, Integer> val2idx= new HashMap<>();
        // private HashMap<Integer, Integer> count2val= new HashMap<>();
        public RandomizedSet1() {

        }

        public boolean insert(int val) {
            if(val2idx.containsKey(val)){
                return false;
            }
            nums[++idx] = val;
            val2idx.put(val, idx);
            return true;
        }

        public boolean remove(int val) {
            if(!val2idx.containsKey(val)){
                return false;
            }
            int local = val2idx.remove(val);
            //将最大count的值设置给当前count，删除最大count条目
            if(local != idx){
                val2idx.put(nums[idx], local);
            }
            nums[local] = nums[idx--];
            return true;
        }

        public int getRandom() {
            //从0～size之间取一个数 获取该数实际值
            return nums[random.nextInt(idx+1)];
        }
    }
    // 238 除自身以外数组的乘积 中等
    public int[] productExceptSelf(int[] nums) {
        int[] left = new int[nums.length];
        int[] right = new int[nums.length];
        int nowLeft = 1;
        int nowRight = 1;
        for(int i=0; i<nums.length; ++i) {
            nowLeft *= nums[i];
            nowRight *= nums[nums.length-i-1];
            left[i] = nowLeft;
            right[nums.length-i-1] = nowRight;
        }
        int[] result = new int[nums.length];
        for(int i=0; i<nums.length; ++i) {
            result[i] = (i-1>=0? left[i-1] : 1)*(i+1<nums.length? right[i+1] : 1);
        }
        return result;
    }
    //最佳题解 思路是差不多的
    public int[] productExceptSelf1(int[] nums) {
        int length = nums.length;
        int[] answer = new int[length];

        // answer[i] 表示索引 i 左侧所有元素的乘积
        // 因为索引为 '0' 的元素左侧没有元素， 所以 answer[0] = 1
        answer[0] = 1;
        for (int i = 1; i < length; i++) {
            answer[i] = nums[i - 1] * answer[i - 1];
        }

        // R 为右侧所有元素的乘积
        // 刚开始右边没有元素，所以 R = 1
        int R = 1;
        for (int i = length - 1; i >= 0; i--) {
            // 对于索引 i，左边的乘积为 answer[i]，右边的乘积为 R
            answer[i] = answer[i] * R;
            // R 需要包含右边所有的乘积，所以计算下一个结果时需要将当前值乘到 R 上
            R *= nums[i];
        }
        return answer;
    }
}
