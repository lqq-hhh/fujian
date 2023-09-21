package com.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Solution {
    /**
     * day 01 0314
     */
    //我自己的解法 过于丑陋）
    public ListNode addTwoNumbersOwn(ListNode l1, ListNode l2) {
        ListNode result = new ListNode();
        int flag = 0;
        int middle =  l1.val + l2.val + flag;
        result.val = middle%10;
        ListNode initial = result;
        while (l1 != null || l2 != null){
            if(l1 != null && l2 != null){
                middle =  l1.val + l2.val + flag;
                result.val = middle%10;
                //flag判定 进位判定
                if(middle >= 10){
                    flag = 1;
                }else {
                    flag = 0;
                }
                l1 = l1.next;
                l2 = l2.next;
            }else if(l1 != null){
                middle = l1.val + flag;
                result.val = middle%10;
                //flag判定 进位判定
                if(middle >= 10){
                    flag = 1;
                }else {
                    flag = 0;
                }
                l1 = l1.next;
            }else{
                middle = l2.val + flag;
                result.val = middle%10;
                //flag判定 进位判定
                if(middle >= 10){
                    flag = 1;
                }else {
                    flag = 0;
                }
                l2 = l2.next;
            }
            if(l1 != null || l2 != null){
                result.next = new ListNode();
                result = result.next;
            }
        }
        //检测最终进位
        if(flag == 1){
            result.next = new ListNode(1);
        }
        return initial;
    }
    //别人的解法begin
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        return addTwoNumbers(l1, l2,0);
    }
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2,int carry) {
        ListNode resultNode = new ListNode();
        int l1Val = l1 != null?l1.val:0;
        int l2Val = l2!= null?l2.val:0;
        //当前节点的值
        int resultNodeVal = (l1Val + l2Val + carry)%10;
        //进位
        int carryNext = (l1Val + l2Val + carry)/10;
        resultNode.val = resultNodeVal;
        ListNode l1Next = l1 != null?l1.next: null;
        ListNode l2Next = l2 != null?l2.next: null;
        if(l1Next != null || l2Next != null || carryNext > 0){
            resultNode.next = addTwoNumbers(l1Next,l2Next,carryNext);
        }
        return resultNode;
    }
    //别人的解法end
    public static void run(){
        ListNode l1 = new ListNode(4);
        ListNode l2 = new ListNode(3);

        l1.next = new ListNode(3);
        l2.next = new ListNode(2);
        ListNode i1 = l1;
        ListNode i2 = l2;
        l1 = l1.next;
        l2 = l2.next;
        l1.next = new ListNode(2);
        l2.next = new ListNode(1);
        l1 = l1.next;
        l1.next = new ListNode(1);
        ListNode l3 = addTwoNumbers(i1,i2);
        for(;l3.next != null;l3=l3.next){
            System.out.println(l3.val);
        }
//        for(;i1 != null;i1=i1.next){
//            System.out.println(i1.val);
//        }
//        for(;i2 != null;i2=i2.next){
//            System.out.println(i2.val);
//        }
    }

    /**
     * day 02 0315
     */
//      问题1 非重字符串最大值
    public static int lengthOfLongestSubstring(String s) {
/*
 * 代码运行时间过长原因：运用了三层for循环遍历 耗时长
 */
        int length = s.length();
        if(length == 0) return 0;
        int maxnum=1;
        for (int i=0;i<length;++i){
            for(int j=i+1;j<length;++j){
                if(copy(s,j,i)){
                    break;
                }else {
                    maxnum = Math.max(maxnum,j-i+1);
                }
            }
        }
        return maxnum;
    }
    //判断是否与之前的字符有重复
    public static boolean copy(String s,int compare,int start){
        for(int i=start;i<compare;++i){
            if(s.charAt(i) == s.charAt(compare)) return true;
        }
        return false;
    }
    public int lengthOfLongestSubstring1(String s) {
            // 记录字符上一次出现的位置
            int[] last = new int[128];
            for(int i = 0; i < 128; i++) {
                last[i] = -1;
            }
            int n = s.length();

            int res = 0;
            int start = 0; // 窗口开始位置
            for(int i = 0; i < n; i++) {
//                这简直神来之笔） 将char类型存储成int类型
//                窗口移动的逻辑：如果在此之前存在与当前字符相同，则窗口移动到上一次该字符出现之后
                int index = s.charAt(i);
                start = Math.max(start, last[index] + 1);
                res   = Math.max(res, i - start + 1);
                last[index] = i;
            }

            return res;
        }
    public static void run031501(String s){
        System.out.println(lengthOfLongestSubstring(s));
    }
//    问题2 寻找两个正序数组的中位数
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
    int l1 = nums1.length;
    int l2 = nums2.length;
    int length = l1+l2;
    double result;
    int[] bind = new int[length];
    int i=0;
    int j =0;
    int k=0;
    for (;k<l2 && j<l1;i++){
        if(nums1[j] < nums2[k]) {
            bind[i] = nums1[j];
            j++;
        }else {
            bind[i] = nums2[k];
            k++;
        }
    }
    if(j<l1){
        for(;i<length;j++,i++) bind[i] = nums1[j];
    }else {for(;i<length;k++,i++) bind[i] = nums2[k];}
    int middle = (int)Math.floor((length)/2);
    if(length%2 != 0)return (double)bind[middle];
    result = 1.0*(bind[middle] + bind[middle-1])/2;
    return result;
}
//      问题3：回文字符串 小心超时！
// 思路：记录所有字符出现的位置
// 从头开始遍历所有字符的
// 然后遍历当前字符的所有位置，判断中间的字符是否组成回文
    public String longestPalindrome(String s) {
    //初始化
    int length = s.length();
    if(length<2) return s;
    int[][] occur = new int[128][length+1];
    for(int i=0;i<128;i++){
        for (int j=0;j<length+1;j++){
            occur[i][j] = -1;
        }
    }
    int a=0;
    //存储所有字符出现的索引
    for(int i=0;i<s.length();i++){
        int index = s.charAt(i);
        while(occur[index][a] != -1){
            a++;
        }
        occur[index][a] = i;
        a=0;
    }
    int start = 0;//字符串起始位置
    int end = 0;//字符串结束位置
    for(int i=0;i<s.length();i++){
        int index = s.charAt(i);
        int j=0;
        while(occur[index][j] != -1){
            int k=i,g=occur[index][j];
            for(;k<g;k++,g--){
                if(s.charAt(k) != s.charAt(g)) break;
            }
            if(k>=g){
                if(occur[index][j]-i>end-start){
                    end = occur[index][j];
                    start = i;
                }
            }
            j++;
        }
    }

    return s.substring(start,end+1);
}
//      问题3 改进之后的解法：还是慢QAQ 但是内存用的更少了
//      加油加油！！！！这才第二天！！！！
    public String longestPalindrome2(String s){
        int start = 0;
        int end = 0;
        int length = s.length();
        if(length<2) return s;
        for(int i=0;i<length;++i){
            for(int j=length-1;j>i;--j){
                int g=i;
                int k=j;
                System.out.println(j+" + "+i);
                for(;g<k;++g,--k){
                    if(s.charAt(g) != s.charAt(k)) break;
                }
                if(g>=k){
                    if(j-i>end-start){
                        System.out.println(j+" + "+i);
                        end=j;
                        start=i;
                    }
                }
            }
        }
        System.out.println(start);
        System.out.println(end);
        return s.substring(start,end+1);
    }
    /*改进思路：
     * 1、使用了我不熟悉的函数.toCharArray():将字符串转化为字符数组，方便调用
     * 2、使用了迭代函数替代for循环
     * 3、为了减少所用时间添加了以下语句：
     *         if(p == (n -1) || (n - p)<(end - start + 1)/2)return;
     *         帮助在剩余长度不足的情况下直接进行返回
     * 4、本质上也是暴力解法！ 但是由于转化为数组之后，字符串的比较快速了很多（？
     * 5、最重要的是！快速的解法使用了中心扩散法则，更换回文字符的中心
     * */
    /*厉害解法：
    int start = 0;
    int end = 0;
    public String  longestPalindrome1(String s){
        if(s.length()<2){
            return s;
        }
        char[] c = s.toCharArray();
        longestPalindromeAt(c,0);
        return s.substring(start,end + 1);
    }
    private void longestPalindromeAt(char[] c,int p){
        int a = p;
        int b = p;
        int n = c.length;
        if(p == (n -1) || (n - p)<(end - start + 1)/2)return;
     //这里判断临近的字符是否相等，就可以组成偶数长度的回文字符串
     //同时考虑了一长条相同字符的情况
        while (b<n - 1 && c[b] == c[b+1])b++;
        p = b;
        while(a>0 && b<n-1 && c[a - 1] == c[b + 1]){
            a--;
            b++;
        }
        if((end - start)<(b - a)){
            end = b;
            start = a;
        }
        longestPalindromeAt(c,++p);
    }
    //问题三解法end
     **/

    /**day03 0316
     *1、2为美团笔试题
     */
    /*
     * 1、最小修改字符
     * 思路：偶数个的相同数字修改次数为     n/2
     * 奇数个的相同数字修改次数为  (n-1)/2
     * 相加可得结果
     */

    /*
     * 2、最佳时刻（没做出来QAQ）
     * 用遍历迭代不行，因为可能会出现某一区间有多个重叠处，没法进行比较
     */
    /*
     *失败的解法www
     */
    public int[] getBestTime(int n, int[] s,int[] t) {
        int index = 0;
        int[] result = {1,1};
        if(s.length == 1) return result;
        int[][] order = new int[n][2];
        for (int i = 0; i < n; ++i) {
            order[i][0] = s[i];
            order[i][1] = t[i];
        }
        for (int i = 0; i < n; ++i) {
            // 设定一个标记，若为true，则表示此次循环没有进行交换，也就是待排序列已经有序，排序已经完成。
            boolean flag = true;
            for (int j = 0; j < n - i; j++) {
                if (order[j][0] > order[j + 1][0]) {
                    int[] tmp = order[j];
                    order[j] = order[j + 1];
                    order[j + 1] = tmp;
                    flag = false;
                }
            }
            if (flag) {
                break;
            }
        }
        int[] back = new int[n];
        for(int j=0;j<n-1;++j){
            back[j] = liuxing(order[j],n,order,result[0]);
        }
        return result;
    }
    public int liuxing(int[]old, int compareIndex,int[][] order,int number){
        //超限
        if(compareIndex == order.length) {
            return number;
        }
        //无交叉部分
        if(old[1] < order[compareIndex][0]) {
            liuxing(old,--compareIndex,order,number);
        }
        //有交叉部分 替换old数组的前后取值
        old[0] = Math.max(old[0],order[compareIndex][0]);
        old[1] = Math.min(old[1],order[compareIndex][1]);
        int back = liuxing(old,--compareIndex,order,number);
        number = Math.max(back,number);
        return number;
    }
    /**
     * 试着用网上的思路解：遍历所有时间节点，获取每个时间节点所包含的流星数
     */
    public int[] getBestTime2(int n, int[] s,int[] t){
        if(1 == n) return new int[]{1, 1};
        int starMax = 1;
        int starAppear = 0;
        int[] storeAppear = new int[1000];//存储节点的出现次数
        for(int i=0;i<n;i++){
            int compare1 = s[i];//进行比较的节点
            int compare2 = t[i];//进行比较的节点
            if(storeAppear[compare1] == 0) {
                for (int j = 0; j < n; j++) {
                    if (compare1 <= t[j] && compare1 >= s[j]) {
                        storeAppear[compare1]++;
                    }
                    starMax = Math.max(storeAppear[compare1], starMax);
                }
            }
            if(storeAppear[compare2] == 0){
                for(int k=0;k<n;k++){
                    if(compare2 <= t[k] && compare2 >= s[k]){
                        storeAppear[compare2]++;
                    }
                    starMax = Math.max(storeAppear[compare2],starMax);
                }
            }
        }
        for(int j=0;j<1000;j++){
            if(storeAppear[j] !=0){
                if(starMax == storeAppear[j]) starAppear++;
            }
        }
        if(starAppear == 0) starAppear++;
        return new int[]{starMax,starAppear};
    }
    /**
     * 加油加油QAQ
     * 动态规划问题：
     * ★ 如果一个问题，可以把所有可能的答案穷举出来，
     * 并且穷举出来后，发现存在重叠子问题，就可以考虑使用动态规划。
     *
     * 比如一些求最值的场景：
     * 如最长递增子序列、最小编辑距离、背包问题、凑零钱问题等等
     * 都是动态规划的经典应用场景。
     *
     * 动态规划的思路：
     * 穷举分析
     * 确定边界
     * 找出规律，确定最优子结构
     * 写出状态转移方程
     */

    /**
     * day04 0317
     */
    /**
     * 问题一：N字形变换
     */
    //又是太慢了QAQ
    public String convert(String s, int numRows) {
        String result = "";
        int length = s.length();
        //如果不满足折叠就直接返回
        if(numRows >= length || numRows == 1) return s;
        String[] rows = new String[numRows];//存储每一行的字符
        for(int g=0;g<numRows;g++)rows[g]="";
        int repeat = 2*numRows - 2;
        for(int i= 0;i<length;i+=repeat){
            for(int j=0;j<numRows;++j){
                if(i+j>=length) break;
                rows[j] = rows[j].concat(""+s.charAt(i + j)+"");
                if(0<j && j<numRows-1 && (i+repeat-j)<length){
                    rows[j] = rows[j].concat(""+s.charAt(i+repeat - j)+"");
                }
            }
        }
        for(int k=0;k<numRows;++k){
            result = result.concat(rows[k]);
        }
        return result;
    }
    /**
     * 示例解法：
     */
    /**
     *     public String convert(String s, int numRows) {
     *         if (numRows == 1) {
     *             return s;
     *         }
     *         int step = numRows + numRows - 2;
     *         int down = step, up = 0;
     *         int h = 0, l = 0, inedx = 0, len = s.length();
     *         char[] chars = new char[len];
     *         //不分行 跳转到该行之后的那个字符
     *         boolean isDown = true;
     *         for (int i = 0; i < len; i++) {
     *             chars[i] = s.charAt(inedx);
     *             if (h == 0 || h == numRows - 1) {
     *                 inedx += step;
     *             } else if (isDown) {
     *                 inedx += down;
     *             } else {
     *                 inedx += up;
     *             }
     *             isDown = !isDown;
     *             if (inedx >= len) {
     *                 down -= 2;
     *                 up += 2;
     *                 isDown = true;
     *                 inedx = ++h;
     *             }
     *         }
     *         return new String(chars);
     *     }
     */
    /**
     * 问题二：数字反转
     */
    public int reverse(int x) {
        String flag = "";
        if(0==x) return 0;
        if(x<0) flag = "-";
        long re = Math.abs((long)x);
        String mi = ""+ re +"";
//        System.out.println(re);
        char[] middle = mi.toCharArray();
        int length = middle.length;
        if(length>12) return 0;
        int i=0;
        while(i<length-i-1){
            char temp = middle[i];
            middle[i] = middle[length-i-1];
            middle[length-i-1] = temp;
            ++i;
        }

        long result = Long.parseLong(flag.concat(new String(middle)));
//        System.out.println(result);
        if(result<Math.pow(-2,31) || result>Math.pow(2,31)-1) return 0;
        int result1 = Integer.parseInt(flag.concat(new String(middle)));
        return result1;
//            return 0;
    }
    /**
     * 示例解法：每次取最后一位
     * 数字处理有简便方法的呀QAQ %10 超好用的 别忘了
     */
    /**
     * class Solution {
     *     public int reverse(int x) {
     *         int res = 0;
     *         while(x!=0) {
     *             //每次取末尾数字
     *             int tmp = x%10;
     *             //判断是否 大于 最大32位整数
     *             if (res>214748364 || (res==214748364 && tmp>7)) {
     *                 return 0;
     *             }
     *             //判断是否 小于 最小32位整数
     *             if (res<-214748364 || (res==-214748364 && tmp<-8)) {
     *                 return 0;
     *             }
     *             res = res*10 + tmp;
     *             x /= 10;
     *         }
     *         return res;
     *     }
     * }
     */
    /**
     * 问题三：字符串转换整数
     */
    public int myAtoi(String s) {
        int length = s.length();
        int i=0;
        String flag = "";
        if(s.equals("")) return 0;
        if(s.length() == 1 &&(s.charAt(0)<'0' || s.charAt(0)>'9')) return 0;
        while (i<s.length() && s.charAt(i) == ' '){
            ++i;
        }
        if(i<s.length() && (s.charAt(i) == '-' || s.charAt(i) == '+')) {
            flag = "" + s.charAt(i) + "";
            ++i;
        }
        if(s.charAt(i) < '0' || s.charAt(i) > '9') return 0;
        int start = i;
        int end = i;
        while (s.charAt(end) <= '9' && s.charAt(end) >= '0'){
            if(end == length - 1) break;
            end++;
        }
        if (s.charAt(end) > '9' || s.charAt(end) < '0') end--;
        long mi = Long.parseLong(flag.concat(s.substring(start,end+1)));
//        System.out.println(start+" + "+end);
        if(mi < Math.pow(-2,31)) return -2147483648;
        if(mi > Math.pow(2,31)-1) return 2147483647;
        return Integer.parseInt(flag.concat(s.substring(start,end+1)));
    }
    /**
     * class Solution {
     *     public int myAtoi(String s) {
     *         int sign = 1, result = 0, i = 0;
     *         // 去除前导空格
     *         while (i < s.length() && s.charAt(i) == ' ') {
     *             i++;
     *         }
     *         // 判断正负号
     *         if (i < s.length() && (s.charAt(i) == '+' || s.charAt(i) == '-')) {
     *             sign = s.charAt(i) == '+' ? 1 : -1;
     *             i++;
     *         }
     *         // 转换数字
     *         while (i < s.length() && Character.isDigit(s.charAt(i))) {
     *             int digit = s.charAt(i) - '0';
     *             // 判断是否超出范围
     *             if (result > Integer.MAX_VALUE / 10 || (result == Integer.MAX_VALUE / 10 && digit > 7)) {
     *                 return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
     *             }
     *             result = result * 10 + digit;
     *             i++;
     *         }
     *         return sign * result;
     *     }
     * }
     */
    /**
     * day05 0318
     */
    /**
     * 问题一：回文数字
     * 思路一：转成字符串 依次头尾比较
     * 思路二：不转化为字符串 ，通过%10来取数字，分前后半段
     * class Solution {
     *     public boolean isPalindrome(int x) {
     *         if(x< 0 || (x % 10 == 0 && x != 0)) return false;
     *         int sum = 0;
     *         while(x > sum)
     *         {
     *             sum = sum * 10 + x % 10;
     *             x /= 10;
     *         }
     *         return x == sum || x == sum / 10;
     *     }
     * }
     */
    /**
     *问题二：正则表达式匹配
     */
    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        for (int i = 0; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (p.charAt(j - 1) == '*') {
                    dp[i][j] = dp[i][j - 2] || (i > 0 && (s.charAt(i - 1) == p.charAt(j - 2) || p.charAt(j - 2) == '.') && dp[i - 1][j]);
                } else {
                    dp[i][j] = i > 0 && dp[i - 1][j - 1] && (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '.');
                }
            }
        }
        return dp[m][n];
    }
    /**
     * 动态匹配方程：看本子上的笔记
     */

    /**
     * day06 0320 (19号休息)
     */
    /**
     * 问题一：盛水最多的容器
     */
    public int maxArea(int[] height) {
//        暴力求解 果然超时了
//        int length = height.length;
//        for(int i=0;i<length;++i){
//            for(int j=i+1;j<length;++j){
//                max = Math.max(max,Math.min(height[i],height[j])*(j-i));
//            }
//        }
        int max = 0;
        int left = 0;
        int right = height.length-1;
        while(left<right){
            max=Math.max(max,(right-left)*(Math.min(height[right],height[left])));
            if(height[left]<height[right]){
                left++;
            }else{
                right--;
            }
        }
        return max;
    }
    /**
     * 示例解法：双指针（？）
     * 总体思路：从最宽的两侧入手，接连排除偏小的一侧
     * 排除思路：由于宽度已经很宽了，那么由更矮一侧的柱子组成的容器不可能更多了
        示例解法：
     *     public int maxArea(int[] height) {
     *         int l = 0, r = height.length - 1;
     *         int maxArea = 0;
     *         while (l < r) {
     *             int area = (r - l) * Math.min(height[l], height[r]);
     *             int minH = Math.min(height[l], height[r]);
     *             maxArea = Math.max(maxArea, area);
     *             // 快速跳过这步可太妙了
     *             while (height[l] <= minH && l < r) {
     *                 l++;
     *             }
     *             while (height[r] <= minH && l < r) {
     *                 r--;
     *             }
     *         }
     *         return maxArea;
     *     }
     */
    /**
     * day07 0322 (21号摆)
     */
    /**
     * 问题一：整数转罗马数字
     * @param num
     * @return
     */
    public String intToRoman(int num) {
        String re = "";
        int ge = num%10;
        int shi = ((num-ge)%100)/10;
        int bai = ((num-ge-shi*10)%1000)/100;
        int qian = (num-ge-shi*10-bai*100)/1000;
        if(qian != 0) {
            for (int i = 0; i < qian; i++) re = re.concat("M");
        }
        if(bai!=0){
            if(bai == 9){
                re = re.concat("CM");
            }else if(bai > 4){
                re = re.concat("D");
                for (int i = 0; i < bai-5; i++) re = re.concat("C");
            }else if(bai == 4){
                re = re.concat("CD");
            }else {
                for (int i = 0; i < bai; i++) re = re.concat("C");
            }
        }
        if(shi!=0){
            if(shi == 9){
                re = re.concat("XC");
            }else if(shi > 4){
                re = re.concat("L");
                for (int i = 0; i < shi-5; i++) re = re.concat("X");
            }else if(shi == 4){
                re = re.concat("XL");
            }else {
                for (int i = 0; i < shi; i++) re = re.concat("X");
            }
        }
        if(ge != 0){
            if(ge == 9){
                re = re.concat("IX");
            }else if(ge > 4){
                re = re.concat("V");
                for (int i = 0; i < ge-5; i++) re = re.concat("I");
            }else if(ge == 4){
                re = re.concat("IV");
            }else {
                for (int i = 0; i < ge; i++) re = re.concat("I");
            }
        }
        return re;
    }
    /**
     * 改进：取各个位的数值可以用更简便的方法，用if判断没有switch case快
     *         int qian = num/1000;
     *         int bai = num%1000/100;
     *         int shi = num%100/10;
     *         int ge = num%10;
     */

    /**
     * 问题二：罗马数字转数字
     */
    public int romanToInt(String s) {
        int re = 0;
        int len = s.length();
        if(len == 0) return 0;
        for(int i=len-1;i>=0;--i){
            if('I' == s.charAt(i)){
                if(len-1 == i) re += 1;
                else if('I' == s.charAt(i+1)) re += 1;
                else if('I' != s.charAt(i+1)) re -= 1;
            }
            else if('X' == s.charAt(i)){
                if(len-1 == i) re += 10;
                else if('X' == s.charAt(i+1)) re += 10;
                else if('L' == s.charAt(i+1) || 'C'==s.charAt(i+1)) re -= 10;
                else re += 10;
            }
            else if('C' == s.charAt(i)){
                if(len-1 == i) re += 100;
                else if('C' == s.charAt(i+1)) re += 100;
                else if('D' == s.charAt(i+1) || 'M'==s.charAt(i+1)) re -= 100;
                else re += 100;
            }
            else if('V' == s.charAt(i)) re += 5;
            else if('L' == s.charAt(i)) re += 50;
            else if('D' == s.charAt(i)) re += 500;
            else if('M' == s.charAt(i)) re += 1000;
        }
        return re;    }
    /**
     * 问题三：最长公共前缀
     */
    public String longestCommonPrefix(String[] strs) {
        int len = strs.length;
        if(len == 1) return strs[0];
        int minLen = strs[0].length();
        String re = "";
        boolean flag = true;
        for(int i=1;i<len;++i) minLen = Math.min(minLen,strs[i].length());
        for(int j=0;j<minLen;++j){
            for(int k=1;k<len;++k){
                if(strs[0].charAt(j) != strs[k].charAt(j)){
                    flag = false;
                    break;
                }
            }
            if (flag) re = re.concat(""+strs[0].charAt(j));
            else return re;
        }
        return re;
    }
    /**
     * 问题四：三数之和
     */
    //果然超时了QAQ
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> re = new ArrayList<>();
        ArrayList<Integer> posi = new ArrayList<>();
        ArrayList<Integer> neg = new ArrayList<>();
        int zero = 0;
        boolean flag = true;
        for (int num : nums) {
            flag = true;
            if (num > 0) {
                if (flag) posi.add(num);
            } else if (num < 0) {
                if (flag) neg.add(num);
            }
            else zero++;
        }
        //三零
        if(zero>2) re.add(Arrays.asList(0,0,0));
        //两正一负
        for(int i=0;i<posi.size()-1;++i){
            for(int j=i+1;j<posi.size();++j){
                for(int k=0;k<neg.size();++k){
                    if(posi.get(i)+posi.get(j)+neg.get(k) == 0){
                        if(re != null){
                                if(!re.contains(Arrays.asList(posi.get(i),posi.get(j),neg.get(k))) && !re.contains(Arrays.asList(posi.get(j),posi.get(i),neg.get(k)))){
                                    re.add(Arrays.asList(posi.get(i),posi.get(j),neg.get(k)));
                            }
                        }
                    }
                }
            }
        }
        //两负一正
        for(int i=0;i<neg.size()-1;++i){
            for(int j=i+1;j<neg.size();++j){
                for(int k=0;k<posi.size();++k){
                    if(neg.get(i)+neg.get(j)+posi.get(k) == 0){
                        if(re != null){
                            if(!re.contains(Arrays.asList(neg.get(i),neg.get(j),posi.get(k))) && !re.contains(Arrays.asList(neg.get(j),neg.get(i),posi.get(k))) ){
                                re.add(Arrays.asList(neg.get(i),neg.get(j),posi.get(k)));
                            }
                        }
                    }
                }
            }
        }
        //有零 一正一负
        if(zero !=0){
            for(int i=0;i<posi.size();++i){
                for(int j=0;j<neg.size();++j){
                    if(posi.get(i)+neg.get(j) == 0){
                        if(re != null){
                            if(!re.contains(Arrays.asList(0,posi.get(i),neg.get(j)))){
                                re.add(Arrays.asList(0,posi.get(i),neg.get(j)));
                            }
                        }
                    }
                }
            }
        }
        return re;
    }
    /*  解题思路:
        解法1: 双指针法 ↓
        首先将数组排序，然后固定一个元素nums[i],使用双指针分别从前后两个方向向中间迭代[i+1,n)位置上元素。判断三元素和是否等于0。如果等于0，则可构成1个三元组。
        当三数之和小于0时，左指针右移一位；反之和大于0时，右指针左移一位。三元组索引两两不同，所以指针相交时迭代结束。

        时间复杂度: O(n^2)
        空间复杂度: O(n)，使用一个HashTable标记已统计过三元组的元素避免重复。
     */
    //使用双指针
    /**
     * 问题五：找到最相近的三数之和
     */
    public int threeSumClosest(int[] nums, int target) {
        int len = nums.length;
        int re = nums[0]+nums[1]+nums[2];
        if(len == 3) return re;
        int mi;
        Arrays.sort(nums);
        for(int i=0;i<len-2;++i){
            int head = nums[i];
            int left = i+1;
            int right = len-1;
            while(left<right){
                mi = nums[left] + nums[right] + head;
                re = Math.abs(mi-target)<Math.abs(re-target)?mi:re;
                if(mi>target) right--;
                else if(mi<target) left++;
                else return target;
            }
        }
        return re;
    }
    /**
     * 问题六：电话号码的数字组合
     */
    public List<String> letterCombinations(String digits) {
        int len = digits.length();
        ArrayList<String> re = new ArrayList<>();
        if(len == 0) return re;
        String[] store = new String[10];
        store[2] = "abc";
        store[3] = "def";
        store[4] = "ghi";
        store[5] = "jkl";
        store[6] = "mno";
        store[7] = "pqrs";
        store[8] = "tuv";
        store[9] = "wxyz";
        ArrayList<String> mi = new ArrayList<>();
        for(int j=0;j<store[Integer.parseInt(""+digits.charAt(0))].length();++j)
            mi.add(""+store[Integer.parseInt(""+digits.charAt(0))].charAt(j));
        if(len == 1) return mi;
        StringBuffer middle = new StringBuffer(5);
        for(int i=1;i<len;++i){
            re.clear();
            for(String midd:mi){
                for(int j=0;j<store[Integer.parseInt(""+digits.charAt(i))].length();++j){
                    middle.append(midd+store[Integer.parseInt(""+digits.charAt(i))].charAt(j));
//                    concat(""+store[Integer.parseInt(""+digits.charAt(i))].charAt(j));
//                    System.out.println(middle);
                    re.add(middle.toString());
                }
            }
            mi = (ArrayList<String>) re.clone();
        }
        return  re;
    }
    public void backtrack(List<String> combinations, String[] phoneMap, String digits, int index, StringBuffer combination) {
        if (index == digits.length()) {
            combinations.add(combination.toString());
        } else {
            char digit = digits.charAt(index);
            String letters = phoneMap[Integer.parseInt(digit + "")];
            int lettersCount = letters.length();
            for (int i = 0; i < lettersCount; i++) {
                combination.append(letters.charAt(i));
                backtrack(combinations, phoneMap, digits, index + 1, combination);
                combination.deleteCharAt(index);
            }
        }
    }
    /**
     * 示例解法：回溯算法
     */
    //添加这个动作只在最后
    /**
     *     public List<String> letterCombinations(String digits) {
     *         List<String> combinations = new ArrayList<String>();
     *         if (digits.length() == 0) {
     *             return combinations;
     *         }
     *         Map<Character, String> phoneMap = new HashMap<Character, String>() {{
     *             put('2', "abc");
     *             put('3', "def");
     *             put('4', "ghi");
     *             put('5', "jkl");
     *             put('6', "mno");
     *             put('7', "pqrs");
     *             put('8', "tuv");
     *             put('9', "wxyz");
     *         }};
     *         backtrack(combinations, phoneMap, digits, 0, new StringBuffer());
     *         return combinations;
     *     }
     *
     *     public void backtrack(List<String> combinations, Map<Character, String> phoneMap, String digits, int index, StringBuffer combination) {
     *         if (index == digits.length()) {
     *             combinations.add(combination.toString());
     *         } else {
     *             char digit = digits.charAt(index);
     *             String letters = phoneMap.get(digit);
     *             int lettersCount = letters.length();
     *             for (int i = 0; i < lettersCount; i++) {
     *                 combination.append(letters.charAt(i));
     *                 backtrack(combinations, phoneMap, digits, index + 1, combination);
     *                 combination.deleteCharAt(index);
     *             }
     *         }
     *     }
     */
    /**
     * day 08 0323
     */
    /**
     * 问题一：四数之和
     */
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> re = new ArrayList<>();
        int len = nums.length;
        Arrays.sort(nums);

        //四个数字锁定两端进行遍历
        int head = 0;
        long compare = 0;
        for(;head<len-3;++head){
            if(nums[head]>target/4) return re;
            if(head>0 && nums[head]==nums[head-1])continue;
            for(int tail = len-1;tail>head+2;--tail){
                if(nums[tail]<target/4) break;
                if(tail<len-1 && nums[tail] == nums[tail+1]) continue;
                int left = head+1;
                int right = tail-1;
                while(left<right){
                    compare = (long)nums[head]+nums[left]+nums[right]+nums[tail];
                    if(compare == target){
                        if(!re.contains(Arrays.asList(nums[head],nums[left],nums[right],nums[tail])))
                            re.add(Arrays.asList(nums[head],nums[left],nums[right],nums[tail]));
                        left++;
                        right--;
                    }
                    else if(compare<target){
                        while (left<right && nums[left] == nums[left+1]) ++left;
                        ++left;
                    }else{
                        while (left<right && nums[right] == nums[right-1]) --right;
                        --right;
                    }
                }
            }
        }
        return re;
    }
    /**
     * day 09 0325
     */
    /**
     * 问题一：删除链表的倒数第N个点
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if(head == null) return head;
        ListNode listNode =  new ListNode();//存储头结点
        listNode.next = head;
        int len = 1;
        while(head.next!=null){
            head = head.next;
            len++;
        }
        head = listNode;
        int i=0;
        while (i<len-n){
            head = head.next;
            ++i;
        }
        if(head.next.next != null) head.next = head.next.next;
        else head.next = null;
        return listNode.next;
    }
    /**
     * 问题二：有效的括号
     */
    public boolean isValid(String s) {
        char[] compare = s.toCharArray();
        if(compare.length%2 != 0) return  false;//长度为奇数直接false
        int[] store = {0,0,0};
        int[] flag = new int[compare.length+2];
        int i=0;
        for (char c : compare) {
            if (c == '('){
                i++;
                store[0]++;
                flag[i] = 0;
            }
            else if (c == '{') {
                i++;
                flag[i] = 1;
                store[1]++;
            }
            else if (c == '[') {
                i++;
                flag[i] = 2;
                store[2]++;
            }
            else if (c == ')') {
                if(store[0]<=0 || flag[i] != 0) return false;
                store[0]--;
                i--;
            }
            else if (c == '}') {
                if(store[1]<=0 || flag[i] != 1) return false;
                store[1]--;
                i--;
            }
            else if (c == ']') {
                if(store[2]<=0 || flag[i] != 2) return false;
                store[2]--;
                i--;
            }
        }
        return (store[0]==0 && store[1] == 0 && store[2] == 0);
    }
    /**
     * 问题三：合并两个有序链表
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if(list1 == null || list2 == null) return list1==null?list2:list1;
        ListNode head = new ListNode();
        ListNode add = head;
        while(list1 != null || list2 != null){
            add.next = new ListNode();
            add = add.next;
            if(list1 != null && list2 !=null){
                if(list1.val < list2.val){
                    add.val = list1.val;
                    list1 = list1.next;
                }else{
                    add.val = list2.val;
                    list2 = list2.next;
                }
            }else if(list1 == null){
                add.val = list2.val;
                list2 = list2.next;
            }else{
                add.val = list1.val;
                list1 = list1.next;
            }
        }
        return head.next;
    }
    /**
     * 问题四：括号生成
     */
    ArrayList<String> re = new ArrayList<>();
    public List<String> generateParenthesis(int n) {
        generate(n,0,0,"");
        ArrayList<String> back = new ArrayList<>();
        return re;
    }
    public void generate(int n,int l,int r,String now){
        if(l+r == 2*n){
            re.add(now);
        }else {
            String now1 = "";
            if(l<n){
                now1 = now.concat("(");
                generate(n,l+1,r,now1);
            }
            if(r<n && l>r){
                now1 = now.concat(")");
                generate(n,l,r+1,now1);
            }
        }
    }
    /**
     * 问题五：合并K个升序列表
     */
    public ListNode mergeKLists(ListNode[] lists) {
        ListNode head = new ListNode();
        ListNode add = head;
        while(lists != null){
            int min = 10001;
            int flag = -1;
            for(int i=0;i<lists.length;++i){
                if(lists[i]!=null && lists[i].val<min){
                    min = lists[i].val;
                    flag = i;
                }
            }
            if(flag == -1) break;
            add.next = new ListNode();
            add = add.next;
            add.val = min;
            if(lists[flag] != null){
                lists[flag] = lists[flag].next;
            }
        }
        return head.next;
    }
    /**
     * 示例解法：两两结合进行合并
     *     public ListNode mergeKLists(ListNode[] lists) {
     *         if(lists.length==0){
     *             return null;
     *         }
     *         return split(lists,0,lists.length-1);
     *
     *     }
     *     private ListNode split(ListNode[] lists,int i,int j){
     *         if(i==j){
     *             return lists[i];
     *         }
     *         int m=(i+j)/2;
     *         ListNode left=split(lists,i,m);
     *         ListNode right=split(lists,m+1,j);
     *         return mergeTwoList(left,right);
     *
     *     }
     *     private ListNode mergeTwoList(ListNode list1,ListNode list2){
     *         ListNode s=new ListNode(-100,null);
     *         ListNode p=s;
     *         while(list1!=null &&list2!=null){
     *             if(list1.val<list2.val){
     *                 p.next=list1;
     *                 list1=list1.next;
     *                 p=p.next;
     *             }else{
     *                 p.next=list2;
     *                 list2=list2.next;
     *                 p=p.next;
     *             }
     *         }
     *         if(list1==null){
     *             p.next=list2;
     *         }
     *         if(list2==null){
     *             p.next=list1;
     *         }
     *         return s.next;
     *     }
     */
    /**
     * day 10 0326
     * 走过路过不要错过！ 这里是哈希表专场！
     */
    /**
     * 问题一：串联所有单词的子串
     */
    //果然又超时了QAQ
    ArrayList<Integer> re1 = new ArrayList<>();
    public List<Integer> findSubstring(String s, String[] words) {
        int len = words.length*words[0].length();
        if(s.length()<len) return new ArrayList<>();
        subStringConcat(s,words,0,"");
        ArrayList<Integer> re2 = new ArrayList<>();
        //去重
        for (Integer k:re1
        ) {
            if(!re2.contains(k)) re2.add(k);
        }
        return re2;
    }
    public void subStringConcat(String s,String[] words,int index,String now){
        if(s.contains(now)){
            if(index == words.length){
                int find = s.indexOf(now);
                //多次匹配
                while(find!=-1){
                    re1.add(find);
                    find = s.indexOf(now,find+1);
                }
                return;
            }
            //拷贝数组，防止值一直改变
            String[] compare = new String[words.length];
            System.arraycopy(words, 0, compare, 0, words.length);
            for(int i=0;i<words.length;++i){
                if(i>0 && !words[i - 1].equals(""))compare[i-1] = words[i-1];
                if(!words[i].equals("")){
                    String now1 = now.concat(words[i]);
                    compare[i] = "";
                    subStringConcat(s,compare,index+1,now1);
                }
            }
        }
    }
    /**
     * 示例解法：哈希表滑动窗口
     * 思路为滑动窗口，充分利用定长的思路来考虑
     * 维护一个哈希表用来存储当前窗口中的单词数量，并与目标单词相比较
     */
    /**
     * class Solution {
     *     public List<Integer> findSubstring(String s, String[] words) {
     *         List<Integer> res = new ArrayList<Integer>();
     *         int m = words.length, n = words[0].length(), ls = s.length();
     *         for (int i = 0; i < n; i++) {
     *             if (i + m * n > ls) {
     *                 break;
     *             }
     *             Map<String, Integer> differ = new HashMap<String, Integer>();
     *             for (int j = 0; j < m; j++) {
     *                 String word = s.substring(i + j * n, i + (j + 1) * n);
     *                 differ.put(word, differ.getOrDefault(word, 0) + 1);
     *             }
     *             for (String word : words) {
     *                 differ.put(word, differ.getOrDefault(word, 0) - 1);
     *                 if (differ.get(word) == 0) {
     *                     differ.remove(word);
     *                 }
     *             }
     *             for (int start = i; start < ls - m * n + 1; start += n) {
     *                 if (start != i) {
     *                     String word = s.substring(start + (m - 1) * n, start + m * n);
     *                     differ.put(word, differ.getOrDefault(word, 0) + 1);
     *                     if (differ.get(word) == 0) {
     *                         differ.remove(word);
     *                     }
     *                     word = s.substring(start - n, start);
     *                     differ.put(word, differ.getOrDefault(word, 0) - 1);
     *                     if (differ.get(word) == 0) {
     *                         differ.remove(word);
     *                     }
     *                 }
     *                 if (differ.isEmpty()) {
     *                     res.add(start);
     *                 }
     *             }
     *         }
     *         return res;
     *     }
     * }
     */
    /**
     * 问题二：有效的数独
     * www被答案背刺了，人家没用哈希表
     */
    public boolean isValidSudoku(char[][] board) {
        HashMap<Integer,Integer> hang = new HashMap<>();//行（数字，）
        HashMap<Integer,Integer> lie = new HashMap<>();//列（列号，值）
        HashMap<Integer,Integer> fangGe = new HashMap<>();//方格（方格号，值）
        for(int i=0;i<9;++i){
            hang.clear();
            lie.clear();
            fangGe.clear();
            for(int j=0;j<9;++j){
                if(board[i][j] != '.'){
                    int mi = Integer.parseInt(board[i][j]+"");
                    hang.put(mi,1+hang.getOrDefault(mi,0));
                    if(hang.get(mi)>1) return false;
                }
                if(board[j][i] != '.') {
                    int mi = Integer.parseInt(board[j][i] + "");
                    lie.put(mi, 1 + lie.getOrDefault(mi, 0));
                    if (lie.get(mi) > 1) return false;
                }
            }
        }
        for(int le=0;le<7;le+=3){
            for(int k=0;k<7;k+=3){
                for(int i=0;i<3;++i){
                    for(int j=0;j<3;++j){
                        if(board[le+i][k+j] != '.'){
                            int mi = Integer.parseInt(board[le+i][k+j] + "");
                            fangGe.put(mi, 1 + fangGe.getOrDefault(mi, 0));
                            if (fangGe.get(mi) > 1) return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    /**
     * 问题三：跳跃游戏
     */
    //天天超时俺已经习惯了
    public int jump11(int[] nums) {
        int i=0;
        int index=0;
        while (index<nums.length) {
            if(nums[index]==0) {
                i--;
                index--;
                continue;
            }
            index+=nums[index];
            ++i;
        }
        int min=i;
        min = jump1(nums,0,min,0);
        return min;
    }
    public int jump1(int[] nums,int index,int min,int now){
        if(now>min) return min;
        if(index > nums.length-2) return now;
        int indexNow;
        for(int i=nums[index];i>0;--i){
            indexNow = index+i;
            min = Math.min(min,jump1(nums,indexNow,min,now+1));
        }
        return min;
    }
    /**
     * 示例解法：从最初的一次跳跃开始，查看下一次能跳到最远处，更新最远跳跃处并跳跃到对应节点
     *     public int jump(int[] nums) {
     *     int ans = 0;
     *     int end = 0;
     *     int maxPos = 0;
     *     for (int i = 0; i < nums.length - 1; i++)
     *     {
     *         maxPos = Math.max(nums[i] + i, maxPos);
     *         if (i == end)
     *         {
     *             end = maxPos;
     *             ans++;
     *         }
     *     }
     *     return ans;
     *     }
     */
    /**
     * day 11 0327
     */
    /**
     * 问题一：统计只差一个字符的子串数目
     */
    public int countSubstrings(String s, String t) {
        int count = 0;
        char[] charS = s.toCharArray();
        char[] charT = t.toCharArray();
        count = countSubstrings(charS,charT,1);
        return count;
    }
    public int countSubstrings(char[] s, char[] t,int length){//子串数目count以及窗口长度length
        int count = 0;
        if(length>s.length || length>t.length) return count;
        for(int i=0;i<s.length-length+1;++i){//s起始位置
            for(int j=0;j<t.length-length+1;++j){//t起始位置
                boolean flag = s[i]==t[j];
                if(length == 1){
                    if(!flag) count+=1;
                }else {
                    for(int le=1;le<length;le++){
                        boolean flag1 = s[i+le]==t[j+le];
                        if(!flag && !flag1) {
                            flag = true;
                            break;
                        }
                        if(!flag1) flag = false;
                    }
                    if(!flag) count+=1;
                }
            }
        }
        count += countSubstrings(s,t,length+1);
        return count;
    }
    /**
     * 问题二：最长有效括号
     */
    public int longestValidParentheses(String s) {
        if(s.isEmpty()) return 0;
        int max = 0;
        char[] chars = s.toCharArray();
        int i=0;
        while(i<chars.length && chars[i] != '(') i++;
        max = longestValidParentheses(chars,i,max);
        return max;
    }
    public int longestValidParentheses(char[] s,int index,int max) {
        if(max>s.length-index) return max;
        int order = 0;
        for(int i=index;i<s.length;++i){
            if(s[i] == '(') order+=1;
            else order -=1;
            if(0==order) max=Math.max(max,i-index+1);
            else if(0>order) break;
        }
        max = Math.max(max,longestValidParentheses(s,index+1,max));
        return max;
    }
    /**
     * 问题三：搜索旋转排序数组
     */
    public int search(int[] nums, int target) {
        int[] copy = new int[nums.length];
        int i=0;
        while(i+1<nums.length && nums[i]<nums[i+1]) ++i;
        if(i<nums.length-1)System.arraycopy(nums,i+1,copy,0,nums.length-i-1);
        System.arraycopy(nums,0,copy,nums.length-i-1,i+1);
        int left=0;
        int right=nums.length-1;
        int com = (left+right)/2;
        //二分法查找
        while(left <= right){
            com = (right+left)/2;
            if(target>copy[com]) {
                left = com+1;
            } else if(target<copy[com]){
                right = com-1;
            }else {
                System.out.println(copy[com]+" com="+com+" i="+i);
                if(com>=nums.length-i-1) return com-(nums.length-i-1);
                else return i+com+1;
            }
        }
        return -1;
    }
    /**
     * 费劲巴拉搞这么复杂！实际上就是两个升序列表，不用转换还麻烦，直接看target在哪个数组里面就丢哪个里面查它！
     * 不需要还复制一个，转换下标QAQ
     *         int[] copy = new int[nums.length];
     *         int i=0;
     *         while(i+1<nums.length && nums[i]<nums[i+1]) ++i;
     *         int left=0;
     *         int right=0;
     *         if(target>=nums[0]){
     *             left=0;
     *             right=i;
     *         }else{
     *             left=i+1;
     *             right=nums.length-1;
     *         }
     *         int com;
     *         //二分法查找
     *         while(left <= right){
     *             com = (right+left)/2;
     *             if(target>nums[com]) {
     *                 left = com+1;
     *             } else if(target<nums[com]){
     *                 right = com-1;
     *             }else {
     *                 return com;
     *             }
     *         }
     *         return -1;
     */

    /**
     *day 12 0328
     */
    /**
     * 问题一：最短公共超序列
     */
    public String shortestCommonSupersequence(String str1, String str2) {
        String ret = "";
        if(str1.length()>str2.length()){
            String mid = str1;
            str1 = str2;
            str2 = mid;
        }
        //默认前短后长
        int[][] find = new int[str1.length()][str2.length()];
        int[][] find3 = new int[str2.length()][str1.length()];
        for(int i=0;i<str1.length();i++){
            for(int j=0;j<i+1;++j){
                if(str1.charAt(i) == str2.charAt(j)){
                    if(j>0) find[i][j] = find[i][j-1]+1;
                    else find[i][j] += 1;
                }else {
                    if(j>0) find[i][j] = find[i][j-1]+1;
                    else find[i][j] += 1;
                }
            }
        }
        for(int i=0;i<str2.length();i++){
            for(int j=0;j<i+1 && j<str1.length();++j){
                if(str2.charAt(i) == str1.charAt(j)){
                    if(j>0) find3[i][j] = find3[i][j-1]+1;
                    else find3[i][j] += 1;
                }else {
                    if(j>0) find3[i][j] = find3[i][j-1]+1;
                    else find3[i][j] += 1;
                }
            }
        }
        int k=0,g=0;
        while (k<str1.length() && g<str2.length()) {
            if(find[k][g]>find3[g][k]) {
                ret = ret.concat(""+str1.charAt(k));
                g+=find[k][g];
                k++;
                continue;
            }
            ret = ret.concat(""+str2.charAt(g));
            k+=find3[g][k];
            ++g;
        }
//        while(k<str1.length()-find2[str2.length()-1]){
//            ret = ret.concat(""+str1.charAt(k));
//            ++k;
//        }
//        while (g<str2.length()-find1[str1.length()-1]){
//            ret = ret.concat(""+str2.charAt(g));
//            ++g;
//        }

        return ret;
    }
    /**
     * day13 0329
     */
    /**
     * 问题一：删除有序数组中的重复项
     */
    public int removeDuplicates(int[] nums) {
        if(nums.length == 1) return 1;
        int flag=0;
        int le = 0;
        int ri = 1;
        while (ri<nums.length){
            if(nums[ri]!=nums[le]){
                flag++;
                nums[flag]=nums[ri];
            }
            le++;
            ri++;
        }
        return flag;
    }
    /**
     * 问题二：移除元素
     */
    public int removeElement(int[] nums, int val) {
        int flag=0;
        int check=0;
        if(nums.length == 1 && nums[0] ==val) return 0;
        while (check<nums.length){
            if(nums[check] != val){
                nums[flag] = nums[check];
                flag++;
            }
            check++;
        }
        return flag;
    }
    /**
     * 问题三：找到第一个匹配元素的下标
     */
    public int strStr(String haystack, String needle) {
        //needle为需要找到的字符串
        int check;
        int compare=0;
        int len1=haystack.length();
        int len2=needle.length();
        while (compare<len1-len2+1){
            check=1;
            if(haystack.charAt(compare) == needle.charAt(0)){
                while (check<len2 && haystack.charAt(compare+check) == needle.charAt(check)){
                    check++;
                }
                if(check==len2) return compare;
            }
            compare++;
        }
        return -1;
    }
    /**
     * 问题四：两数相除
     */
    public int divide(int dividend, int divisor) {
        if(dividend == 0) return 0;
        int flag=1;
        if((dividend<0&&divisor>0)||(divisor<0&&dividend>0)){
            flag=-1;
        }
        int result = 0;
        return result*flag;
    }
    /**
     * 问题五：统计字典序元音字符串的数量
     */
    public int countVowelStrings(int n) {
        int num=0;
        num = countVowelStrings(n,0,5);
        return num;
    }
    public int countVowelStrings(int n,int index,int type){
        if(index == n) return 1;
        int num=0;
        switch (type){
            case 5:
                for(int i=1;i<6;++i) num += countVowelStrings(n,index+1,i);
                break;
            case 4:
                for(int i=1;i<5;++i) num += countVowelStrings(n,index+1,i);
                break;
            case 3:
                for(int i=1;i<4;++i) num += countVowelStrings(n,index+1,i);
                break;
            case 2:
                for(int i=1;i<3;++i) num += countVowelStrings(n,index+1,i);
                break;
            case 1:
                num += countVowelStrings(n,index+1,1);
        }
        return num;
    }
    /**
     * 问题六：最远建筑
     */
    public int furthestBuilding(int[] heights, int bricks, int ladders) {
        int[] bricksUsed = new int[heights.length];
        int i=0;//当前位置
        while(i<heights.length-1){
            if(heights[i]>=heights[i+1]) {
                i++;
                continue;
            }
            if(bricks>=heights[i+1]-heights[i]) {
                bricksUsed[i] = heights[i+1] - heights[i];
                bricks = bricks - heights[i+1] + heights[i];
                i++;
                continue;
            }
            if(ladders>0){
                int max = 0;
                for(int j=1;j<i;++j){
                    max = bricksUsed[j]>bricksUsed[max]?j:max;
                }
                if(bricksUsed[max]<heights[i+1]-heights[i]){
                    i++;
                    ladders--;
                    continue;
                }
                bricks += bricksUsed[max];
                bricksUsed[max] = 0;
                ladders--;
            }else break;
        }
        System.out.println(Arrays.toString(bricksUsed));
        return i;
    }
    public int furthestBuilding(int[] heights, int bricks, int ladders,int now) {
        if(now == heights.length-1) return now;
        int max=now;
        if(heights[now]>heights[now+1]) max = Math.max(furthestBuilding(heights,bricks,ladders,now+1),max);
        else {
            if(ladders>0) max = Math.max(furthestBuilding(heights,bricks,ladders-1,now+1),max);
            if(heights[now+1]-heights[now]<=bricks) max=Math.max(max,furthestBuilding(heights,bricks-heights[now+1]-heights[now],ladders,now+1));
        }
        return max;
    }
    //示例解法使用了大顶堆来减少查找最大值的时间
    /**
     * 问题七：交换两两相邻的节点
     */
    public ListNode swapPairs(ListNode head) {
        if(head == null || head.next == null) return head;
        ListNode change = new ListNode();
        change = head.next;
        ListNode next = head.next;
        head.next = next.next;
        next.next = head;
        ListNode three = new ListNode();
        while(head.next != null && head.next.next != null){
            next = head.next;
            head.next = next.next;
            three = head.next.next;
            head.next.next = next;
            next.next = three;
            head = next;
        }
        return change;
    }
    /**
     * 问题八：下一个排列
     */
    public void nextPermutation(int[] nums) {
        int length = nums.length;
        if(length == 1) return;
        if(length==2) {
            int mid = nums[length-1];
            nums[length-1] = nums[length-2];
            nums[length-2] =mid;
            return;
        }
        int i=length-1;
        while(i>0 && nums[i-1]>=nums[i]){
            --i;
        }
        if(i==0){
            int left=0;
            int right=length-1;
            while(left<right) {
                int mid = nums[left];
                nums[left] = nums[right];
                nums[right] = mid;
                left++;
                right--;
            }
            return;
        }
        int j=0;
        while (nums[i-1]>=nums[length-1-j]) j++;
        int mid = nums[i-1];
        nums[i-1] = nums[length-1-j];
        nums[length-1-j] = mid;
        int left=i;
        int right=length-1;
        while(left<right) {
            mid = nums[left];
            nums[left] = nums[right];
            nums[right] = mid;
            left++;
            right--;
        }
    }
    /**
     * day 14 0330
     */
    //问题一：搜索插入位置
    public int searchInsert(int[] nums, int target) {
        int len = nums.length;
        if(nums[0]>=target) return 0;
        if(nums[len-1]<target) return len;
        int left=0;
        int right=len-1;
        while(left<right){
            int mid = (left+right)>>>1;
            if(nums[mid]<target){
                left=mid+1;
            }else if(nums[mid]>target) right=mid-1;
            else return mid;
        }
        if(nums[left]<target) return left+1;
        return left;
    }
    /**
     * day 15 0331
     * 明天美团笔试呜呜
     */
    //问题一：小美的用户名
    public void xiaomei1() {
        Scanner sc = new Scanner(System.in);
        int len = Integer.parseInt(sc.nextLine());
        for(int i=0;i<len;i++){
            String get = sc.nextLine();
            boolean num=false;
            int length=get.length();
            if(length<2){
                System.out.println("Wrong");
            }else if(Character.isLowerCase(get.charAt(0)) || Character.isUpperCase(get.charAt(0))){
                for(int j=1;j<length;j++){
                    char now = get.charAt(j);
                    if(Character.isDigit(now)){
                        num=true;
                    }else if(!(Character.isLowerCase(now) || Character.isUpperCase(now))){
                        num=false;
                        break;
                    }
                }
                if(num) System.out.println("Accept");
                else System.out.println("Wrong");
            }else System.out.println("Wrong");
        }
    }
    /**
     * day 26 0401
     */
    /**
     * 问题一：
     */
    public void test040101(){
        Scanner sc = new Scanner(System.in);
        int len = Integer.parseInt(sc.nextLine());
        String[] nums = sc.nextLine().split(" ");
        int num = Integer.parseInt(sc.nextLine());
        String[] cul = sc.nextLine().split(" ");
        float result = 0;
        for(String i:nums){
            result += Integer.parseInt(i);
        }
        if(num==0) return;
        for(int i=0;i<num;++i){
            int index = Integer.parseInt(cul[i*2]);
            float result1 = result - Integer.parseInt(nums[index])-Integer.parseInt(nums[index-1]);
            String sign = cul[i*2+1];
            switch (sign) {
                case "-":
                    result1 += Integer.parseInt(nums[index-1]) - Integer.parseInt(nums[index]);
                    break;
                case "+":
                    result1 += Integer.parseInt(nums[index-1]) + Integer.parseInt(nums[index]);
                    break;
                case "*":
                    result1 += Integer.parseInt(nums[index]) * Integer.parseInt(nums[index - 1]);
                    break;
                case "/":
                    result1 += (float) Integer.parseInt(nums[index-1]) / Integer.parseInt(nums[index]);
                    break;
            }
            if(i==num-1) System.out.print(result1);
            else System.out.print(result1+" ");
        }
    }
    public void test002(){
        Scanner sc = new Scanner(System.in);
        int len = Integer.parseInt(sc.nextLine());
        String[] nums = sc.nextLine().split(" ");
        int num = Integer.parseInt(sc.nextLine());
        if(len == 2) {
            System.out.println(Math.abs(Integer.parseInt(nums[1])-Integer.parseInt(nums[0])));
            return;
        }
        for(int i=0;i<nums.length;++i){
            for(int j=i;j<nums.length;++j){
                if(Integer.parseInt(nums[i])>Integer.parseInt(nums[j])) {
                    String mid = nums[i];
                    nums[i] = nums[j];
                    nums[j] = mid;
                }
            }
        }
        int result = 0;
        for(int i=0;i<nums.length-1;++i){
            System.out.println(nums[i]);
            result += Integer.parseInt(nums[i+1]) - Integer.parseInt(nums[i]);
        }
        System.out.print(result);
    }
    /**
     * public static void main(String[] args){
     *         Scanner sc = new Scanner(System.in);
     *         String[] len = sc.nextLine().split(" ");
     *         int number = Integer.parseInt(len[0]);//收藏夹数量
     *         int options = Integer.parseInt(len[1]);//操作数量
     *         System.out.println(number+" "+options);
     *         String[] hang2 = sc.nextLine().split(" ");
     *         String[] hang3 = sc.nextLine().split(" ");
     *         String[] hang4 = sc.nextLine().split(" ");
     *         int[] now = new int[number];
     *         boolean flag=false;
     *         for(int i=0;i<options;++i){
     *             int result=0;
     *             switch (hang2[i]){
     *                 case "1":
     *                     for(int j=Integer.parseInt(hang3[i])-1;j<Integer.parseInt(hang4[i]);++j) result+=now[j];
     *                     if(flag)System.out.print(" "+result);
     *                     else {
     *                         flag=true;
     *                         System.out.print(result);
     *                     }
     *                     break;
     *                 case "0":
     *                     now[Integer.parseInt(hang3[i])-1] = Integer.parseInt(hang4[i]);
     *             }
     *         }
     *     }
     */
    /**
     *     public static void main(String[] args){
     *         Scanner sc = new Scanner(System.in);
     *         int len = Integer.parseInt(sc.nextLine());//杯子数量
     *         if(len == 0) return;
     *         String[] cup = sc.nextLine().split(" ");//杯子容量
     *         String[] cupNow = sc.nextLine().split(" ");//杯子初始状态
     *         String[] magic = sc.nextLine().split(" ");//杯子需要消耗的魔法
     *         int test = Integer.parseInt(sc.nextLine());//练习次数
     *         if(test == 0) return;
     *         String[] aim = sc.nextLine().split(" ");//目标填满杯子
     *         boolean flag=false;
     *         for(int i=0;i<test;++i){//第几次练习
     *             int now = Integer.parseInt(aim[i]);
     *             int min=1000000;
     *             for(int j=0;j<now;++j){//从第几个杯子开始
     *                 int numberOfWater = 0;
     *                 for(int k=j;k<now;++k){
     *                     numberOfWater+=Integer.parseInt(cup[k])-Integer.parseInt(cupNow[k]);
     *                 }
     *                 min = Math.min(numberOfWater*Integer.parseInt(magic[j]),min);
     *             }
     *             if(flag) System.out.println(" "+min);
     *             else{
     *                 flag=true;
     *                 System.out.println(min);
     *             }
     *         }
     *     }
     */
    /**
 * 第一行一个正整数n表示节点个数。
 *
 * 第二行n-1个正整数p[i](2≤i≤n)表示第 i 个节点的父亲。1号节点是根节点。
 *
 * 第三行n个整数c[i](1≤i≤n)，当c[i] = 1时表示第 i 个节点是红色，c[i] = 2则表示绿色。
 *
 * 数据保证形成合法的树。
 *
 * 对于所有的数据，n≤50000
 */
    /**
     * 对于树还没有进行复习，今天开始看树
     */
    /**
     * 力扣一：二叉树的中序遍历
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        ArrayList<Integer> result = new ArrayList<>();
        if(root==null) return result;
        return inorderTraversal(root,result);
    }
    public ArrayList<Integer> inorderTraversal(TreeNode root,ArrayList<Integer> result) {
        if(root==null) return result;
        result = inorderTraversal(root.left,result);
        result.add(root.val);
        result = inorderTraversal(root.right, result);
        return result;
    }
    /**
     * 力扣二：不同的二叉搜索树 没做出来咳咳
     */
    /*
    示例题解思路：枚举所有根节点i，则左子树的结点值范围为1-(i-1)，右子树的节点值为(i+1)-n
    递归得到左右子树 之后黏贴左右子树到根节点上
     */
    /**
     * 示例题解：class Solution {
     *     public List<TreeNode> generateTrees(int n) {
     *         if (n == 0) {
     *             return new LinkedList<TreeNode>();
     *         }
     *         return generateTrees(1, n);
     *     }
     *
     *     public List<TreeNode> generateTrees(int start, int end) {
     *         List<TreeNode> allTrees = new LinkedList<TreeNode>();
     *         if (start > end) {
     *             allTrees.add(null);
     *             return allTrees;
     *         }
     *
     *         // 枚举可行根节点
     *         for (int i = start; i <= end; i++) {
     *             // 获得所有可行的左子树集合
     *             List<TreeNode> leftTrees = generateTrees(start, i - 1);
     *
     *             // 获得所有可行的右子树集合
     *             List<TreeNode> rightTrees = generateTrees(i + 1, end);
     *
     *             // 从左子树集合中选出一棵左子树，从右子树集合中选出一棵右子树，拼接到根节点上
     *             for (TreeNode left : leftTrees) {
     *                 for (TreeNode right : rightTrees) {
     *                     TreeNode currTree = new TreeNode(i);
     *                     currTree.left = left;
     *                     currTree.right = right;
     *                     allTrees.add(currTree);
     *                 }
     *             }
     *         }
     *         return allTrees;
     *     }
     * }
     */
    public List<TreeNode> generateTrees(int n) {
        ArrayList<TreeNode> result = new ArrayList<>();
        int[] nums = new int[n];
        for(int i=1;i<=n;i++){
            nums[i-1] = i;
        }
        int[] now = new int[n];
        for(int i=1;i<=n;i++){
            nums[i-1]=0;
            TreeNode root = new TreeNode(i);
            for(int k=0;k<n;++k) now[k] = nums[k];
            result.addAll(generateTrees(root,now));
            nums[i-1]=i;
        }
        return result;
    }
    public ArrayList<TreeNode> generateTrees(TreeNode root,int[] nums) {
        System.out.println(Arrays.toString(nums));
        ArrayList<TreeNode> result = new ArrayList<>();
        int[] now = new int[nums.length];
        boolean flag = false;
        for(int i=0;i<nums.length;i++){
            if(nums[i] != 0){
                for(int k=0;k<nums.length;++k) now[k] = nums[k];
                flag=true;
                TreeNode rootChanged = insert(root,nums[i]);
                now[i] = 0;
                System.out.println(Arrays.toString(now));
                result.addAll(generateTrees(rootChanged,now));
            }
        }
        if(!flag) result.add(root);
        return result;
    }
    public TreeNode insert(TreeNode root,int val){
        if(root==null) return null;
        if(root.val == 0){
            root.val = val;
            return root;
        }
        if(root.val>val){
            if(root.left == null) root.left = new TreeNode(val);
            else root.left = insert(root.left,val);
        }else{
            if(root.right == null) root.right = new TreeNode(val);
            else root.right = insert(root.right,val);
        }
        return root;
    }
    /**
     * day 27 0402
     */
    //问题一：相同的树
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if(p==null || q==null) return p==null&&q==null;
        if(p.val != q.val) return false;
        if(!isSameTree(p.left,q.left)) return false;
        return isSameTree(p.right, q.right);
    }
    //问题二：对称二叉树
    //思路：左根右遍历一遍，右根左遍历一遍 对于得到的结果相比较
    public boolean isSymmetric(TreeNode root) {
        if(root==null) return true;
        return isSymmetric(root.left,root.right);
    }
    public boolean isSymmetric(TreeNode left,TreeNode right){
        if(left==null||right==null) return left==null&&right==null;
        if(left.val!=right.val) return false;
        if(!isSymmetric(left.right,right.left)) return false;
        return isSymmetric(left.left,right.right);
    }
    //问题三：二叉树的最大深度
    public int maxDepth(TreeNode root) {
        if(root==null) return 0;
        return Math.max(maxDepth(root.left,1),maxDepth(root.right,1));
    }
    public int maxDepth(TreeNode root,int depth) {
        if(root==null) return depth;
        depth+=1;
        return Math.max(maxDepth(root.left,depth),maxDepth(root.right,depth));
    }
    //问题四：不同的二叉搜索树
    public int numTrees(int n) {
        int[] result = new int[n+1];
        result[0] = 1;
        result[1] = 1;
        for(int i=2;i<=n;i++){
            for(int j=1;j<=i;j++){
                result[i] += result[j-1] * result[i-j];
            }
        }
        return result[n];
    }
    /**
     * day 28 0407 摸鱼了好几天）
     */
    //问题一：交错字符串
    public boolean isInterleave(String s1, String s2, String s3) {
        if(s3.length()!=s1.length()+s2.length()) return false;
//        return isInterleave(0,0,0,s3,s1,s2);
        int len1=s1.length();
        int len2=s2.length();
        int len3=s3.length();
        int j=0,k=0;
        boolean[][] record = new boolean[len1+1][len2+1];
        record[0][0] = true;
        for(int i=1;i<=len3;++i){
            char com = s3.charAt(i-1);
            int index1=j;
            int index2=k;
            if(j<len1 && s1.charAt(j) == com) {
                record[index1+1][index2]=record[index1][index2];
                j++;
            }
            if(k<len2 && s2.charAt(k) == com) record[j][k+1]=record[j][k];
        }
        return record[len1][len2];
//        for(int i=0;i<len3;i++){
//            char com = s3.charAt(i);
//            if(j<len1 && k<len2 &&(com==s1.charAt(j)||j==len1 && k==len2)){
//
//            }
//            if(j<len1 && com==s1.charAt(j)) {
//                j++;
//                continue;
//            }
//            if(k<len2 && com==s2.charAt(k)) k++;
//        }
//        if(j==len1 && k==len2) return true;
    }
    public boolean isInterleave(int index,int i,int j,String s1,String s2,String s3){
        if(index == s1.length()) return true;
        boolean flag = false;
        char com = s1.charAt(index);
        if(i<s2.length() && com==s2.charAt(i)) flag=isInterleave(index+1,i+1,j,s1,s2,s3);
        if(flag) return true;
        if(j<s3.length() && com==s3.charAt(j)) return isInterleave(index+1,i,j+1,s1,s2,s3);
        return false;
    }
    //0409 小红书笔试
    //    public static void main(String[] args){
//        Scanner sc = new Scanner(System.in);
//        String[] line1 = sc.nextLine().split(" ");
//        String[] line2 = sc.nextLine().split(" ");
//        String[] line3 = sc.nextLine().split(" ");
//        ArrayList<int[]> record = new ArrayList<>();
//        int num = Integer.parseInt(line1[0]);
//        int now=0;
//        double max= 0;
//        for(int i=0;i<num;i++){
//            record.add(new int[]{Integer.parseInt(line2[i]),Integer.parseInt(line3[i])});
//            int[] hey = record.get(i);
//            if((double)hey[0]/hey[1]>max) now = i;
//        }
//        max=now;
//        now = Math.max(now,calculate(Integer.parseInt(line1[2]),record.get((int) max),record,Integer.parseInt(line1[1]))[1]);
//        System.out.println(now);
//        return;
//    }

//    public static int[] calculate(int aim,int[] now,ArrayList<int[]> record,int add) {
//        if (aim < now[0]) return new int[]{0,0};
//        else if (aim == now[0]) return now;
//        else {
//            int less = aim - now[0];
//            if (less >= now[0]) {
//                record.add(new int[]{now[0], now[1]});
//                now[0] += now[0];
//                now[1] += now[1] + add;
//                int[] re = calculate(aim, now, record, add);
//                if(re[1]>now[1]) now = re;
//            } else {
//                for (int i = record.size() - 1; i >= 0; --i) {
//                    if (record.get(i)[0] <= less) {
//                        record.add(new int[]{now[0],now[1]});
//                        now[0] += record.get(i)[0];
//                        now[1] += record.get(i)[1];
//                        int[] re = calculate(aim, now, record, add);
//                        if(re[1]>now[1]) now = re;
//                    }
//                }
//            }
//        }
//        return now;
//    }

//    Scanner sc = new Scanner(System.in);
//        int num = Integer.parseInt(sc.nextLine());//球的数量
//        String[] number = sc.nextLine().split(" ");//球上的数字
//        String color = sc.nextLine();//球的颜色
//        int conduct = Integer.parseInt(sc.nextLine());//操作次数
//        String[] time = sc.nextLine().split(" ");//操作时机
//        String[] boll = sc.nextLine().split(" ");//操作数字（0为询问，其他为放入）
//        ArrayList<Integer> numNow = new ArrayList<>();//目前球的值信息（序号，放入时间，值，颜色）
//        int[] put = new int[num+1];//球被放入的时机
//        int now = 0;//目前总数
//        int blue = 0;//篮球数量
//        int red = 0;//红球数量
//        for(int i=0;i<conduct;++i){
//            int ball = Integer.parseInt(boll[i]);//当前加入的球的序号
//            if(i>0) now += (red-blue)*(Integer.parseInt(time[i])-Integer.parseInt(time[i-1]));
//            if(ball==0){
//                numNow.add(now);
//            }
//            else{
//                if(ball>0){//放入球
//                    ball = ball-1;
//                    put[ball] = Integer.parseInt(time[i]);
//                    if(color.charAt(ball) == 'R') red++;
//                    else blue++;
//                    now += Integer.parseInt(number[ball]);
//                }else{
//                    ball = Math.abs(ball)-1;//取出球
//                    if(color.charAt(ball) == 'R') {
//                        red--;
//                        now -= ((Integer.parseInt(number[ball])+Integer.parseInt(time[i])-put[ball]));
//                    }
//                    else {
//                        blue--;
//                        now -= ((Integer.parseInt(number[ball])-Integer.parseInt(time[i])+put[ball]));
//                    }
//                }
//            }
//        }
//        System.out.print(numNow.size());
//        for(Integer out:numNow){
//            System.out.print(" "+out);
//        }
    /**
     * day 29 0412 本周目标 20题
     */
    //001 反转链表 简单
    public ListNode reverseList(ListNode head) {
        ListNode newNode = new ListNode();
        boolean flag=false;
        while(head != null) {
            if(!flag){
                newNode = new ListNode(head.val);
                head=head.next;
                flag=true;
                continue;
            }
            newNode = new ListNode(head.val,newNode);
            head = head.next;
        }
        if(flag)return newNode;
        return head;
    }
    //002 1147 段式回文 每日一题 困难！解决了！！！
    public int longestDecomposition(String text) {
        int head1 = 0;
        int len = text.length();
        if(len == 1)return 1;
        int head2 = len-1;
        int end = len-1;
        int result = 0;
        // char[] text1 = text.toCharArray();
        boolean flag=true;
        while(head1<=head2){
            while(head2>=0 && text.charAt(head1) != text.charAt(head2)){
                head2--;
            }
            if(head1>=head2 || head2-head1<end-head2) {
                result++;
                break;
            }
            for(int i=head1,j=head2;j<=end;i++,j++){
                if(text.charAt(i) != text.charAt(j)) {
                    flag=false;
                    break;
                }
            }
            // System.out.println("head1="+head1+" head2="+head2+" end="+end);
            if(flag) {
                head1 += end-head2+1;
                head2 -= 1;
                result += 2;
                end = head2;
            }else{
                head2 -= 1;
                flag=true;
            }
        }
        return result;
    }
    //003 25 K个一组翻转链表 呜呜做出来了
    public ListNode reverseKGroup(ListNode head, int k) {
        if(k==1) return head;
        ListNode nowNode = new ListNode(head.val);
        ListNode end = nowNode;
        head = head.next;
        for(int i=1;i<k;++i){
            nowNode = new ListNode(head.val,nowNode);
            head = head.next;
        }
//        System.out.println(head.val);
        ListNode Head = nowNode;
        while(head!=null){
            System.out.println("1 "+head.val);
            int i=1;
            ListNode store = new ListNode(head.val);
            ListNode nowHead = head.next;
            while(i<k && nowHead!=null){
//                System.out.println("2 "+head.val);
                store = new ListNode(nowHead.val,store);
                nowHead = nowHead.next;
                ++i;
            }
            if(i==k) {
                end.next = store;
                for(int j=0;j<k;++j) end = end.next;
                if(nowHead == null) break;
                head=nowHead;
            }else{
                end.next = head;
                break;
            }
        }
        return Head;
    }

    /**
     * day 30 0413 本周目标还剩17题
     */
    //004 出现最多的偶数 306ms
    public int mostFrequentEven1(int[] nums) {
        int len = nums.length;
        if(len==0) return -1;
        HashMap<Integer,Integer> store = new HashMap<>();
        HashMap<Integer,Integer> store1 = new HashMap<>();
        int flag = 0;//偶数个数
        for (int aim : nums) {
            if (aim % 2 == 0) {
                if (!store.containsValue(aim)) {
                    store.put(flag, aim);
                    store1.put(aim,1);
                    flag++;
                    continue;
                }
                store1.replace(aim, store1.get(aim) + 1);
            }
        }
        if(flag==0) return -1;
        int min=1000000;
        int currency=0;
        for(int i=0;i<flag;++i){
            int cur = store1.get(store.get(i));
            if(currency<=cur){
                min = Math.min(min,store.get(i));
                currency = cur;
            }
        }
        return min;
    }
    //第二种方法：一次循环解决问题 9ms
    public int mostFrequentEven(int[] nums) {
        int len = nums.length;
        if(len==0) return -1;
        int min=-1;
        int currency=0;
        int[] num = new int[100001];
        for (int j : nums) {
            if (j % 2 == 0) {
                num[j]++;
                if (currency <= num[j]) {
                    if (currency == num[j]) min = Math.min(min, j);
                    else min = j;
                    currency = num[j];
                }
            }
        }
        return min;
    }
    /**
     * day 31 0415 本周目标还剩16题（这不是完全没进展吗！）hh
     */
    //005 1042 不邻接植花 中等
    public int[] gardenNoAdj(int n, int[][] paths) {
        int[] result = new int[n];
        List<int[]> path3 = Arrays.asList(paths);
        ArrayList<int[]> path = new ArrayList<>(path3);
        for (int[] ints : paths) {
            path.add(new int[]{ints[1],ints[0]});
        }
        int[][] arr = path.toArray(new int[path.size()][]);
        Arrays.sort(arr, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0]!=o2[0]){
                    return o1[0]-o2[0];
                }else {
                    return o1[1]-o2[1];
                }
            }
        });
        int point=0;//path数组的指针
        //  for(int i=0;i<arr.length;++i)System.out.println(Arrays.toString(arr[i]));
        for(int i=0;i<n;++i){
            boolean[] flag = new boolean[4];//与它相连的花园所种的花
            int j=0;
            while(point<arr.length&&arr[point][0] == i+1){
                if(result[arr[point][1]-1] != 0){
                    flag[result[arr[point][1]-1]-1] = true;
                }
                point++;
                j++;
            }
            j=0;
            while(flag[j]) ++j;
            result[i] = j+1;
        }
        return result;
    }
    //006 34 在排序数组中查找元素的第一个和最后一个位置
    public int[] searchRange(int[] nums, int target) {
        int len = nums.length;
        if(len==0 || nums[0]>target || nums[len-1]<target) return new int[]{-1,-1};
        if(len==1) {
            if(nums[0] == target) return new int[]{0,0};
            else return new int[]{-1,-1};
        }
        int left=0;
        int right=len-1;
        while(left<=right){
            int mid=(left+right)/2;
            if(nums[mid]==target) break;
            if(nums[mid]<target){
                left=mid+1;
            }else{
                right=mid-1;
            }
        }
        if(left>right) return new int[]{-1,-1};
        int reLeft = (left+right)/2;
        int reRight = reLeft;
        while(reLeft>=0 && nums[reLeft]==target) --reLeft;
        while(reRight<len && nums[reRight]==target) ++reRight;
        return new int[]{reLeft+1,reRight-1};
    }
    //007 42 接雨水 困难
    public int trap(int[] height) {
        int len = height.length;
        if(len<3) return 0;
        int now=0;
        int all=0;
        while(now<len){
            // System.out.println("now="+now+" all="+all);
            int left = now;
            while(left<len-1 && height[left]<height[left+1]) ++left;
            if(left == len-1) return all;
            int leftHight = height[left];
            int hold=0;
            int right=left+1;
            int max=right;
            while(right<len && height[right]<leftHight) {
                if(height[max]<height[right]) max=right;
                hold+=leftHight-height[right];
                ++right;
            }
            if(right==len) {
                height[left]=height[max];
                now=left;
                continue;
            }
            all+=hold;
            now=right;
        }
        return all;
    }
    /**
     * day 32 0416 本周目标还剩13题  加油周日（
     */
    //008 1157 子数组中占绝大多数的元素 困难
    //具体解法见MajorityChecker.java类中，超时了（但我很认真的做）
    //009 38 外观数列 中等
    public String countAndSay(int n) {
        String describe = "1";
        if(n==1) return describe;
        for(int i=1;i<n;++i){
            int len=describe.length();
            String des = "";
            for(int j=0;j<len;){
                int num = 0;
                char now = describe.charAt(j);
                while(j<len && now==describe.charAt(j)){
                    ++j;
                    ++num;
                }
//                System.out.println("num="+num+" now="+now);
                des = des.concat(num+""+now);
//                System.out.println(des);
            }
            describe = des;
        }
        return describe;
    }
    /**
     * day 33 0417 今天是新一周的开始捏 由于没完成上周任务，所以这周题量还是20题 加油捏！
     * 但这不代表上周没完成的题目没有惩罚！ 每日做完了题目还是要加油补前面的！
     */
    //001 2409 统计共同度过的日子数 简单
    public int countDaysTogether(String arriveAlice, String leaveAlice, String arriveBob, String leaveBob){
        try{
            Date dateArriveAlice = new SimpleDateFormat("yyyy-MM-dd").parse(new String("2023-").concat(arriveAlice));
            Date dateLeaveAlice= new SimpleDateFormat("yyyy-MM-dd").parse(new String("2023-").concat(leaveAlice));
            Date dateArriveBob = new SimpleDateFormat("yyyy-MM-dd").parse(new String("2023-").concat(arriveBob));
            Date dateLeaveBob= new SimpleDateFormat("yyyy-MM-dd").parse(new String("2023-").concat(leaveBob));
            Date arrive=dateArriveAlice.getTime()>dateArriveBob.getTime()?dateArriveAlice:dateArriveBob;
            Date leave=dateLeaveAlice.getTime()<dateLeaveBob.getTime()?dateLeaveAlice:dateLeaveBob;
            if(arrive.getTime()>leave.getTime()) return 0;
            return (int) ((leave.getTime()-arrive.getTime())/(24*60*60*1000)+1);
        }catch (Exception e){
            System.out.println("none");
        }
        return 0;
    }
    //002 39 组合总和 中等
     public List<List<Integer>> combinationSum(int[] candidates, int target) {
     List<List<Integer>> list = new ArrayList<>();
     Arrays.sort(candidates);
     // System.out.println(Arrays.toString(candidates));
     if(candidates.length==0 || candidates[0]>target) return list;
     for(int i=0;i<candidates.length;++i){
     if(candidates[i]<=target) {
     List<Integer> list1 = new ArrayList<>();
     list1.add(candidates[i]);
     // System.out.println(Arrays.toString(list1.toArray()));
     list.addAll(combinationSum(candidates,target-candidates[i],list1,i));
     }
     else break;
     }
     return list;
     }
     public List<List<Integer>> combinationSum(int[] candidates, int target,List<Integer> list,int index) {
     List<List<Integer>> result = new ArrayList<>();
     if(target==0) {
     result.add(list);
     return result;
     }else if(target>0){
     for(int i=index;i<candidates.length;++i){
     if(candidates[i]<=target) {
     List<Integer> list1 = new ArrayList<>();
     for(int j=0;j<list.size();++j) list1.add(list.get(j));
     list1.add(candidates[i]);
     // System.out.println(Arrays.toString(list1.toArray()));
     result.addAll(combinationSum(candidates,target-candidates[i],list1,i));
     }
     else break;
     }
     }
     return result;
     }
     //003 40 组合总数II 中等
     /**
     * hiahiahia 想到了新鲜的思路 跟着组合总数的思路走 另外使用一个数组来存储出现的次数 芜湖 俺真天才（bushi
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> list = new ArrayList<>();
        Arrays.sort(candidates);
        // System.out.println(Arrays.toString(candidates));
        if(candidates.length==0 || candidates[0]>target) return list;
        int[] store = new int[candidates.length];
        int[] times = new int[candidates.length];

        for(int i=0,j=0;i<candidates.length;++j){
            store[j] = candidates[i];
            times[j] = 0;
            while(i<candidates.length&&candidates[i]==store[j]) {
                ++i;
                ++times[j];
            }
        }
        // System.out.println(Arrays.toString(store));
        // System.out.println(Arrays.toString(times));
        for(int i=0;i<candidates.length && store[i]!=0;++i){
            if(store[i]<=target) {
                List<Integer> list1 = new ArrayList<>();
                list1.add(store[i]);
                times[i]-=1;
                // System.out.println(Arrays.toString(list1.toArray()));
                list.addAll(combinationSum2(store,target-store[i],list1,i,times));
                times[i]+=1;
            }
            else break;
        }
        return list;
    }
    public List<List<Integer>> combinationSum2(int[] candidates, int target,List<Integer> list,int index,int[] times) {
        // System.out.println(Arrays.toString(times)+" target="+target+" index="+index);
        List<List<Integer>> result = new ArrayList<>();
        if(target==0) {
            result.add(list);
            return result;
        }else if(target>0){
            for(int i=index;i<candidates.length;++i){
                if(times[i]<=0) continue;
                if(candidates[i]<=target) {
                    int[] newTimes = times.clone();
                    newTimes[i]-=1;
                    List<Integer> list1 = new ArrayList<>(list);
                    list1.add(candidates[i]);
                    // System.out.println(Arrays.toString(list1.toArray()));
                    result.addAll(combinationSum2(candidates,target-candidates[i],list1,i,newTimes));
                }else break;
            }
        }
        return result;
    }
    /**
     * day 34 0418 本周题量剩余17题
     */
    //004 1026 节点与其祖先之间的最大差值 中等
    public int maxAncestorDiff(TreeNode root) {
        int max=0;
        if(root.left != null) max=maxAncestorDiff(root.left,root.val,root.val);
        if(root.right != null) max=Math.max(max,maxAncestorDiff(root.right,root.val,root.val));
        return max;
    }
    public int maxAncestorDiff(TreeNode root,int maxParent,int minParent) {
        if(root == null) return 0;
        int max = Math.max(Math.abs(root.val-maxParent),Math.abs(root.val-minParent));
        if(root.val>maxParent) maxParent=root.val;
        if(root.val<minParent) minParent=root.val;
        if(root.left != null) max=Math.max(max,maxAncestorDiff(root.left,maxParent,minParent));
        if(root.right != null) max=Math.max(max,maxAncestorDiff(root.right,maxParent,minParent));
        return max;
    }
    /**
     * day 35 0419 本周题目剩余16题
     */
    //005 1043 分隔数组以得到最大和 中等
    //做错了捏
    public int maxSumAfterPartitioning1(int[] arr, int k) {
        int result=0;
        if(k==1) {
            for (int j : arr) result += j;
            return result;
        }
        int len=arr.length;
        int flag=0;
        while(flag<len){
            System.out.println(result);
            int max=0;
            for(int i=0;i<len;++i){
                max=arr[i]>arr[max]?i:max;
            }
//            int compareA = 0;
            int a=max-1;
//            int compareB = 0;
            int b=max+1;
            int k1=1;
            while(k1<k){
                if(a>=0 && b<len){
                    if(arr[a]<arr[b]){
                        result += arr[max]-arr[a];
                        arr[a] = 0;
                        a--;
                    }else{
                        result += arr[max]-arr[b];
                        arr[b] = 0;
                        b++;
                    }
                }else if(a>=0){
                    result += arr[max]-arr[a];
                    arr[a] = 0;
                    a--;
                }else if(b<len){
                    result += arr[max]-arr[b];
                    arr[b] = 0;
                    ++b;
                }else break;
                k1++;
            }
            arr[max]=0;
            flag += k1;
        }
        return result;
    }
    //示例解法：动态规划
    public int maxSumAfterPartitioning(int[] arr, int k) {
        int n = arr.length;
        int[] d = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int maxValue = arr[i - 1];
            for (int j = i - 1; j >= 0 && j >= i - k; j--) {
                d[i] = Math.max(d[i], d[j] + maxValue * (i - j));
                if (j > 0) {
                    maxValue = Math.max(maxValue, arr[j - 1]);
                }
            }
        }
        return d[n];
    }
    //006 43 字符串相乘 中等
    //不太能用long类型或者int类型直接计算 会有误差
    public String multiply1(String num1, String num2) {
        if(num1.equals("0")||num2.equals("0")) return "0";
        int len1=num1.length();
//        System.out.println(Math.pow(10,2));
        int len2=num2.length();
        long result = 0;
        for(int i=len1-1;i>=0;--i){
            for(int j=len2-1;j>=0;--j){
                result+=Long.parseLong(""+num1.charAt(i))*Math.pow(10,len1-i-1)*Long.parseLong(""+num2.charAt(j))*Math.pow(10,len2-j-1);
            }
        }
        return new String(""+result);
    }
    //示例代码
    public String multiply(String num1, String num2) {
        if(num1.equals("0")||num2.equals("0")) return "0";
        int m=num1.length();
        int n=num2.length();
        int[] arr=new int[m+n];
        for(int i=m-1;i>=0;i--){
            int x=num1.charAt(i)-'0';
            for(int j=n-1;j>=0;j--){
                int y=num2.charAt(j)-'0';
                arr[i+j+1]+=x*y;
            }
        }
        for(int i=m+n-1;i>0;i--){
            arr[i-1]=arr[i-1]+arr[i]/10;
            arr[i]=arr[i]%10;
        }
        int index=arr[0]==0?1:0;
        StringBuilder res=new StringBuilder();
        while(index<m+n){
            res.append(arr[index]);
            index++;
        }
        return res.toString();
    }
    //007 45 跳跃游戏II 中等
    public int jump(int[] nums) {
        int index=0;
        int len=nums.length;
        if(len==1) return 0;
        int jump=0;
        while(index<len){
            // System.out.println("index="+index);
            if(nums[index]+index>=len-1) {
                jump+=1;
                break;
            }
            int max=1;
            for(int i=1;i<=nums[index];++i){
                max = nums[index+i]+index+i>nums[max]+max?index+i:max;
            }
            index=max;
            jump++;
        }
        return jump;
    }
    //008 46 全排列 中等
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> store = new ArrayList<>();
        for(int i=0;i<nums.length;++i){
            List<Integer> list = new ArrayList<>();
            list.add(nums[i]);
            int[] now = nums.clone();
            now[i] = -11;
            store.addAll(permute(now,list));
        }
        return store;
    }
    public List<List<Integer>> permute(int[] nums,List<Integer> now) {
        List<List<Integer>> store = new ArrayList<>();
        boolean flag=false;
        for(int i=0;i<nums.length;++i){
            if(nums[i] == -11) continue;
            flag=true;
            List<Integer> list = new ArrayList<>(now);
            list.add(nums[i]);
            int[] clone = nums.clone();
            clone[i] = -11;
            store.addAll(permute(clone,list));
        }
        if(!flag) store.add(now);
        return store;
    }
    //009 47 全排列II 中等
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<Integer> num = new ArrayList<>();
        HashMap<Integer,Integer> store = new HashMap<>();
        for(int i:nums){
            if(!num.contains(i)) {
                num.add(i);
                store.put(i,1);
            }
            else store.replace(i,store.get(i)+1);
        }
        int[] num1 = new int[num.size()];
        for(int i=0;i<num.size();++i){
            num1[i] = num.get(i);
        }
        List<List<Integer>> result = new ArrayList<>();
        for(int i=0;i<num1.length;++i){
            store.replace(num1[i],store.get(num1[i])-1);
            List<Integer> list = new ArrayList<>();
            list.add(num1[i]);
            result.addAll(permuteUnique(num1,store,list,nums.length));
            store.replace(num1[i],store.get(num1[i])+1);
        }
        return result;
    }
    public List<List<Integer>> permuteUnique(int[] nums,HashMap<Integer,Integer> store,List<Integer> now,int target) {
        // System.out.println(store.toString());
        // System.out.println(now.toString());
        System.out.println(nums.length);
        List<List<Integer>> result = new ArrayList<>();
        if(now.size()==target) {
            result.add(new ArrayList(now));
            return result;
        }
        int i=0;
        for (int num : nums) {
            // System.out.println(++i);
            if (store.get(num) <= 0) continue;
            store.replace(num, store.get(num) - 1);
            now.add(num);
            result.addAll(permuteUnique(nums, store, now, target));
            now.remove(now.size()-1);
            store.replace(num, store.get(num) + 1);
        }
        return result;
    }
    /**
     * day 36 0420 本周题目剩余11题
     */
    //010 1187 使数组严格递增 困难
    public int makeArrayIncreasing1(int[] arr1, int[] arr2) {
        if(arr1.length == 1) return 0;
        Arrays.sort(arr2);
        int flag=0;
        int result=0;
        if(arr1[0]>=arr1[1] || (arr1.length>2 && arr1[2]<=arr1[1])){
            if(arr1[0]>=arr1[1]) {
                if(arr2[0]<arr1[1]) {
                    arr1[1]=arr2[0];
                    result+=1;
                }else return -1;
            }
            if(arr1.length>2 && arr1[2]<=arr1[1]) while(flag<arr2.length && arr1[0]>=arr2[flag]) ++flag;
            if(flag==arr2.length) return -1;
            arr1[1]=arr2[flag];
            result+=1;
        }
        if(arr1.length==2) return result;
        // System.out.println(Arrays.toString(arr2));
        for(int i=0;i<arr1.length-1;++i){
            System.out.println(Arrays.toString(arr1)+" flag="+flag+" result="+result);
            if(i==0||(i<arr1.length-2 && arr1[i+1]>arr1[i] && arr1[i+2]>arr1[i+1])||(i==arr1.length-2 && arr1[i+1]>arr1[i])) continue;
            while(flag<arr2.length && arr1[i]>=arr2[flag]) ++flag;
            if(flag==arr2.length) {
                result = -1;
                break;
            }
            arr1[i+1] = arr2[flag];
            result+=1;
        }
        // System.out.println(Arrays.toString(arr1)+" result="+result);
        return result;
    }
    //示例解法
    /**
     * 其思路如下：使用动态规划，递推公式：
     */
    static final int INF = 0x3f3f3f3f;
    public int makeArrayIncreasing(int[] arr1, int[] arr2) {
        Arrays.sort(arr2);
        List<Integer> list = new ArrayList<Integer>();
        int prev = -1;
        for (int num : arr2) {
            if (num != prev) {
                list.add(num);
                prev = num;
            }
        }
        int n = arr1.length;
        int m = list.size();
        int[][] dp = new int[n + 1][Math.min(m, n) + 1];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], INF);
        }
        dp[0][0] = -1;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= Math.min(i, m); j++) {
                /* 如果当前元素大于序列的最后一个元素 */
                if (arr1[i - 1] > dp[i - 1][j]) {
                    dp[i][j] = arr1[i - 1];
                }
                if (j > 0 && dp[i - 1][j - 1] != INF) {
                    /* 查找严格大于 dp[i - 1][j - 1] 的最小元素 */
                    int idx = binarySearch(list, j - 1, dp[i - 1][j - 1]);
                    if (idx != list.size()) {
                        dp[i][j] = Math.min(dp[i][j], list.get(idx));
                    }
                }
                if (i == n && dp[i][j] != INF) {
                    return j;
                }
            }
        }
        return -1;
    }
    public int binarySearch(List<Integer> list, int low, int target) {
        int high = list.size();
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (list.get(mid) > target) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }
    //011 300 最长递增子序列 中等
    public int lengthOfLIS(int[] nums) {
        if(nums.length==1) return 1;
        if(nums.length==2) return nums[1]>nums[0]?2:1;
        int[] dp = new int[nums.length+1];
        dp[0] = 1;
        int max=1;
        for(int i=1;i<nums.length;++i){
            dp[i] = 1;
            for(int j=0;j<i;++j){
                if(nums[i]>nums[j]) {
                    dp[i] = Math.max(dp[j]+1,dp[i]);
                }
            }
            max=Math.max(max,dp[i]);
        }
        // System.out.println(Arrays.toString(dp));
        return max;
    }
    /**
     * day 37 0422 本周剩余9题 要是赶紧做应该还能行）
     */
    //012 1027 最长等差数列 中等
    public int longestArithSeqLength1(int[] nums) {
        if(nums.length<=2) return nums.length;
        int[][] dp = new int[nums.length][2];
        dp[0][0] = 1;
        dp[1][0] = 1;
        dp[1][1] = nums[1]-nums[0];
        int len = 2;
        for(int i=2;i<nums.length;++i){
            dp[i][0] = 1;
            for(int j=0;j<i;++j){
                if(nums[i]-nums[j] == dp[j][1]) {
                    dp[i][0]+=dp[j][0];
                    dp[i][0] = dp[j][1];
                }
            }
            len = Math.max(len,dp[i][0]);
        }
        return len;
    }
    //示例解法：
    //使用动态规划 遍历所有方差的可能性
    public int longestArithSeqLength(int[] nums) {
        int minv = Arrays.stream(nums).min().getAsInt();
        int maxv = Arrays.stream(nums).max().getAsInt();
        int diff = maxv - minv;
        int ans = 1;
        for (int d = -diff; d <= diff; ++d) {
            int[] f = new int[maxv + 1];
            Arrays.fill(f, -1);
            for (int num : nums) {
                int prev = num - d;
                if (prev >= minv && prev <= maxv && f[prev] != -1) {
                    f[num] = Math.max(f[num], f[prev] + 1);
                    ans = Math.max(ans, f[num]);
                }
                f[num] = Math.max(f[num], 1);
            }
        }
        return ans;
    }

    /**
     * day 38 0423 本周剩余8题 噶油捏
     */
    //013 48 旋转图像 中等
    public void rotate(int[][] matrix) {
        int ceng = matrix.length;
        if(ceng==1) return;
        int start=0;
        for(int i=ceng-1;i>0;i-=2,++start){
            for(int j=0;j<i;++j){
                int store = matrix[start+i-j][start];
                // System.out.println(store);
                matrix[start+i-j][start] = matrix[start+i][start+i-j];
                matrix[start+i][start+i-j] = matrix[start+j][start+i];
                matrix[start+j][start+i] = matrix[start][start+j];
                matrix[start][start+j] = store;
            }
        }
    }
    //014 1105 填充书架 中等
    public int minHeightShelves1(int[][] books, int shelfWidth) {
        if(books.length == 1) return books[0][1];
        List<List<Integer>> store = new ArrayList<>();
        return minHeightShelves(books,shelfWidth,0,store);
    }
    public int minHeightShelves(int[][] books, int shelfWidth,int index,List<List<Integer>> store){
        int height=0;
        int max=0;
        for(List<Integer> integers:store){
            for(Integer i:integers) max=Math.max(max,books[i][1]);
            height+=max;
        }
        System.out.println("lines height="+height+" index="+index+" store="+store.toString());
        if(index==books.length) return height;
        List<List<Integer>> tran = new ArrayList<>(store);//拷贝
        List<Integer> list = new ArrayList<>(index);
        tran.add(list);
        height = minHeightShelves(books,shelfWidth,index+1,tran);
        int width=0;
        if(store.size()>0) for(int i:store.get(store.size()-1)) width+=books[i][0];
        if(shelfWidth-width>=books[index][0]) {
            if(max<books[index][1]) height+=books[index][1]-max;
            tran.remove(tran.size()-1);
            if(tran.size()>0) tran.get(tran.size()-1).add(index);
            else tran.add(new ArrayList<>(index));
            height=Math.min(height,minHeightShelves(books,shelfWidth,index+1,tran));
        }
        return height;
    }
    public int minHeightShelves(int[][] books, int shelfWidth,int now,int index,List<List<Integer>> store) {
        System.out.println("lines height="+now+" index="+index+" store="+store.toString());
        if(index==books.length) {
            int height1 = 0;
            for(int i=0;i<store.get(store.size()-1).size();++i) height1=Math.max(height1,store.get(store.size()-1).get(i));
            return now+height1;
        }
        int min=10000000;
        if(store.isEmpty()) {
            store.add(new ArrayList<Integer>());
            store.get(0).add(index);
//            now += books[0][1];
            min = minHeightShelves(books,shelfWidth,now,index+1,store);
        }else{
            int width=0;
            int height=0;
            for(int i:store.get(store.size()-1)){
                width+=books[i][0];
            }
            List<Integer> list = new ArrayList<>();
            list.add(index);
            List<List<Integer>> tran = new ArrayList<>(store);
            tran.add(list);
            int tran2 = 0;
            int thisHeight = 0;
            for (List<Integer> integers : tran) {
                for (int i = 0; i < integers.size()-1; ++i) thisHeight = Math.max(thisHeight, books[integers.get(i)][1]);
                tran2 += thisHeight;
            }
            min=minHeightShelves(books,shelfWidth,tran2,index+1,tran);
            if(shelfWidth-width>=books[index][0]) {
                thisHeight = 0;
                for(int i=0;i<tran.get(tran.size()-1).size();++i) thisHeight=Math.max(thisHeight,books[tran.get(tran.size()-1).get(i)][1]);
                now+=thisHeight;
                tran.remove(tran.size()-1);
                tran.get(tran.size()-1).add(index);
                min=Math.min(min,minHeightShelves(books,shelfWidth,now,index+1,tran));
            }
        }
        return min;
    }
    //示例解法：使用了动态规划
    public int minHeightShelves(int[][] books, int shelfWidth) {
        //dp[i]表示以第i本书（从1开始数）为结尾的前i本书能叠成的最小高度。
        int n = books.length;
        int[] dp = new int[n+1];
        dp[1] = books[0][1];
        for(int i = 2; i <=n;i++){
            dp[i] = books[i-1][1] + dp[i-1];
            int width = books[i-1][0], height = books[i-1][1];
            for(int j = i-1; j > 0; j--) {
                width += books[j-1][0];
                if(width > shelfWidth) {
                    break;
                }
                height = Math.max(height, books[j-1][1]);
                dp[i] = Math.min(dp[i], dp[j-1] + height);
            }
        }
        return dp[n];
    }
}