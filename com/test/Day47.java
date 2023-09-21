package com.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//0523
public class Day47 {

    //002 1090 受标签影响的最大值 中等
    public int largestValsFromLabels(int[] values, int[] labels, int numWanted, int useLimit) {
        int len = values.length;
        int aim = numWanted;
        int[][] numStores = new int[20000][2];
        List<List<Integer>> labStores = new ArrayList<>();
        int flag=0;
        int max=0;
        int minInNow=-1;
        if(numWanted<useLimit) useLimit=numWanted;
        for(int i=0;i<len;++i){
            System.out.println(max);
            int aimLabel = labels[i];
            int aimValue = values[i];
            if(labStores.isEmpty()) {
                List<Integer> store = new ArrayList<>();
                store.add(i);
                labStores.add(store);
                numStores[aimLabel][0] = 1;
                numStores[aimLabel][1] = values[i];
                minInNow = aimValue;
                max+=aimValue;
                flag++;
            }else if(numStores[aimLabel][0]<useLimit){
                if(flag<numWanted){
                    flag++;
                    max+=aimValue;
                    numStores[aimLabel][0]++;
                    int j=0;
                    while(j<labStores.size()&&labels[labStores.get(j).get(0)] != aimLabel) ++j;
                    if(j!=labStores.size()) {
                        labStores.get(j).add(i);
                        numStores[aimLabel][1] = values[min(labStores.get(j).toArray(),values)];
                    }else{
                        List<Integer> store = new ArrayList<>();
                        store.add(aimValue);
                        labStores.add(store);
                        numStores[aimLabel][1] = aimValue;
                    }
                } else {
                    if(numStores[aimLabel][0]>0 && numStores[aimLabel][1]<aimValue){
                        max+=aimValue-numStores[aimLabel][1];
                        int j=0;
                        while(j<labStores.size()&&labels[labStores.get(j).get(0)] != aimLabel) ++j;
                        int k=0;
                        while (k<labStores.get(j).size()&&values[labStores.get(j).get(k)]!=numStores[aimLabel][1]){
                            // System.out.println(numStores[aimLabel][1]);
                            // System.out.println("aimvalue="+aimValue+" j="+j+" values="+values[labStores.get(j).get(k)]);
                            ++k;
                        }
                        labStores.get(j).remove(k);
                        labStores.get(j).add(i);
                        numStores[aimLabel][1] = values[min(labStores.get(j).toArray(),values)];
                    }
                }
            }else {
                if(numStores[aimLabel][0]>0 && numStores[aimLabel][1]<aimValue){
                    max+=aimValue-numStores[aimLabel][1];
                    int j=0;
                    while(j<labStores.size()&&labels[labStores.get(j).get(0)] != aimLabel) ++j;
                    int k=0;
                    while (k<labStores.get(j).size()&&values[labStores.get(j).get(k)]!=numStores[aimLabel][1]) ++k;
                    labStores.get(j).remove(k);
                    labStores.get(j).add(i);
                    numStores[aimLabel][1] = values[min(labStores.get(j).toArray(),values)];
                }
            }

        }
        return max;
    }
    public int min(Object[] nums,int[] values){
        if(nums.length==0) return 0;
        int min=(int)nums[0];
        for(Object num:nums){
            if(values[(int)num]<min) min=(int)num;
        }
        return min;
    }
    /*
    给出一个区间的集合，请合并所有重叠的区间。
示例 1:
输入: intervals = [[2,6],[8,10],[15,18]，[1,3]]
输出: [[1,6],[8,10],[15,18]]
解释: 区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
示例 2:
输入: intervals = [[1,4],[4,5]]
输出: [[1,5]]
解释: 区间 [1,4] 和 [4,5] 可被视为重叠区间。
     */
    public int[][] test(int[][] intervals){
        List<int[]> stores = new ArrayList<>();
        for(int[] ints:intervals){
            if(stores.isEmpty()) {
                stores.add(ints);
                continue;
            }
            boolean flag = false;//是否与现有的区间重叠
            for(int i=0;i<stores.size();++i){
                int[] store = stores.get(i);
                if(!(store[1]<ints[0]||store[0]>ints[1])) {
                    store[0] = Math.min(store[0],ints[0]);
                    store[1] = Math.max(store[1],ints[1]);
                    stores = emerge(stores,i);
                    flag=true;
                    break;
                }
            }
            if(!flag) stores.add(ints);
        }
        int[][] result = new int[stores.size()][2];
        for(int i=0;i<stores.size();++i){
            result[i] = stores.get(i);
        }
        return result;
    }
    public List<int[]> emerge(List<int[]> stores,int index){
        int[] aim = stores.get(index);
        List<int[]> result = new ArrayList<>();
        result.add(aim);
        for(int[] store:stores){
            boolean flag = false;
            if(!(store[1]<aim[0]||store[0]>aim[1])){
                result.get(0)[0] = Math.min(store[0],aim[0]);
                result.get(0)[1] = Math.max(store[1],aim[1]);
                flag=true;
            }
            if(!flag) result.add(store);
        }
        return result;
    }
 }
