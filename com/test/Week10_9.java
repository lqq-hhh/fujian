package com.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Week10_9 {
    //2512 奖励最顶尖的K名学生 中等
    public List<Integer> topStudents(String[] positive_feedback, String[] negative_feedback, String[] report, int[] student_id, int k) {
        int[][] stores = new int[student_id.length][2];
        int i=1;
        for(String rep: report) {
            int pos = 0;
            int neg = 0;
            for(String po: positive_feedback) if(rep.contains(po)) pos+=1;
            for(String ne: negative_feedback) if(rep.contains(ne)) neg += 1;
            stores[i-1][0] = pos*3 - neg;
            stores[i-1][1] = student_id[i-1];
            i += 1;
        }
        Arrays.sort(stores, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o2[0]-o1[0];
            }
        });
        List<Integer> result = new ArrayList<>();
        i=0;
        for(; i<k; i++) result.add(stores[i][1]);
        return result;
    }
}
