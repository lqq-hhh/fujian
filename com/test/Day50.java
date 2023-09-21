package com.test;

import java.util.*;

//7.28
public class Day50 {
    /**
     * 思路如下
     * 1. 列出所有必经路径 然后得出每个路径的最大值？
     * 先是厘清解题思路 而后考虑具体实现优化可能方向
     * 正向：
     * step1: (无未学习的前置课程)同时学习 计算每层最大值  并加上
     * step2: 筛选出所有能够学习的课程
     * step3:
     *
     * @param n         课程数
     * @param relations 课程关系
     * @param time      各课程所需时间
     * @return
     */
    public int minimumTime1(int n, int[][] relations, int[] time) {
        int result = 0;
        List<Integer>[] newRelations = new List[n];//存所有课程相互依赖
        for (int[] relation : relations) {
            if (null == newRelations[relation[0] - 1]) newRelations[relation[0] - 1] = new ArrayList<>();
            newRelations[relation[0] - 1].add(relation[1] - 1);
        }
        List<Integer> learned = new ArrayList<>();//已学完的课程
        while (learned.size() < n) {
            List<Integer> now = new ArrayList<>();//存当前能学课程
            if (learned.size() == 0) {
                for (int i = 0; i < n; ++i) if (null == newRelations[i]) now.add(i);//一个课程都没学过
            } else {//学习过课程之后找能够学的课程
                for (int k = 0; k < n; ++k) {
                    List<Integer> relation = newRelations[k];
                    if (null == relation) continue;
                    boolean flag = true;
                    for (Integer j : relation) {
                        if (!learned.contains(j)) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) now.add(k);
                }
            }
            //开始学习课程
            int learn = 0;
            for (Integer integer : now) {
                learn = Math.max(learn, time[integer]);
                newRelations[integer] = null;
            }
            result += learn;
            learned.addAll(now);
        }
        return result;
    }

    public int minimumTime(int n, int[][] relations, int[] time) {
        int mx = 0;
        List<Integer>[] prev = new List[n + 1];
        for (int i = 0; i <= n; i++) {
            prev[i] = new ArrayList<Integer>();
        }
        for (int[] relation : relations) {
            int x = relation[0], y = relation[1];
            prev[y].add(x);
        }
        Map<Integer, Integer> memo = new HashMap<Integer, Integer>();
        for (int i = 1; i <= n; i++) {
            mx = Math.max(mx, dp(i, time, prev, memo));
        }
        return mx;
    }

    public int dp(int i, int[] time, List<Integer>[] prev, Map<Integer, Integer> memo) {
        if (!memo.containsKey(i)) {
            int cur = 0;
            for (int p : prev[i]) {
                cur = Math.max(cur, dp(p, time, prev, memo));
            }
            cur += time[i - 1];
            memo.put(i, cur);
        }
        return memo.get(i);
    }

    /**
     * 7.31
     */
    //重排链表
    public void reorderList2(ListNode head) {
        ListNode turn = new ListNode(head.val);//逆转链表
        ListNode flag = head.next;
        ListNode initial = head;
        int length = 1;
        while (flag != null) {
            length += 1;
            ListNode now = new ListNode(flag.val);
            flag = flag.next;
            now.next = turn;
            turn = now;
        }
        boolean choose = false;
        head = head.next;
        while (length > 1) {
            if (!choose) {
                head = turn;
                turn = turn.next;
            } else {
                head = initial;
                initial = initial.next;
            }
            length -= 1;
            choose = !choose;
            head = head.next;
        }
    }

    public void reorderList(ListNode head) {
        if (head == null) {
            return;
        }
        ListNode mid = middleNode(head);
        ListNode l1 = head;
        ListNode l2 = mid.next;
        mid.next = null;
        l2 = reverseList(l2);
        mergeList(l1, l2);
    }

    public ListNode middleNode(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
    }

    public void mergeList(ListNode l1, ListNode l2) {
        ListNode l1_tmp;
        ListNode l2_tmp;
        while (l1 != null && l2 != null) {
            l1_tmp = l1.next;
            l2_tmp = l2.next;

            l1.next = l2;
            l1 = l1_tmp;

            l2.next = l1;
            l2 = l2_tmp;
        }
    }

    /**
     * 8.3
     */
    //删除注释
    //没考虑到的情况：一行里面有超级多/**/ // 结对怎么处理 还是应该做迭代循环
    public List<String> removeComments(String[] source) {
        if (source.length == 0) return null;
        List<String> result = new ArrayList<>();
        boolean flag = false;//是否处于一个注释块中
        for (String sour : source) {
            String now = "";
            int location = 0;
            if (!flag) {
                int location1 = sour.indexOf("//", 0);
                int location2 = sour.indexOf("/*", 0);
                if (location1 < 0 && location2 < 0) now = sour;
                else if (location1 < 0) {
                    location = sour.indexOf("/*", 0);
                    now = now.concat(sour.substring(0, location));
                    flag = true;
                    int end = sour.indexOf("*/", location + 2);
                    if (end > 0) {
                        now = now.concat(sour.substring(end + 2));
                    }
                } else if (location2 < 0) {
                    now = sour.substring(0, location1);
                } else {
                    location = Math.min(location1, location2);
                    now = now.concat(sour.substring(0, location));
                    if (location1 > location2) {
                        flag = true;
                        int end = sour.indexOf("*/", location + 2);
                        if (end > 0) {
                            now = now.concat(sour.substring(end + 2));
                        }
                    }
                }
                if (!now.equals("")) result.add(now);
            } else {
                if (sour.contains("*/")) {
                    flag = false;
                    location = sour.indexOf("*/", 0);
                    int end1 = sour.indexOf("//", location + 2);//该行后面还存在//
                    int end2 = sour.indexOf("/*", location + 2);//该行后面还存在/*
                    if (end1 > 0 && end2 > 0) {
                        now = sour.substring(location + 2, Math.min(end1, end2));
                        flag = end1 <= end2;
                    } else if (end1 > 0) {
                        now = sour.substring(location + 2, end1);
                    } else if (end2 > 0) {
                        now = sour.substring(location + 2, end2);
                    } else {
                        now = sour.substring(location + 2);
                    }
                    if (!now.equals("")) {
                        int length = result.size();
                        if (length > 0) {
                            now = result.get(length - 1).concat(now);
                            result.remove(length - 1);
                            result.add(now);
                        } else result.add(now);
                    }
                }
            }
        }
        return result;
    }

    public int solution(int D, String S) {
        Map<String, Integer> numbers = new HashMap<>();
        numbers.put("one", 1);
        numbers.put("two", 2);
        numbers.put("three", 3);
        numbers.put("four", 4);
        numbers.put("five", 5);
        return D * numbers.get(S);
    }

    public int solution(String S) {
        if (S.length() == 0) return 0;
        int result = 1;
        int length = S.length();
        for (; result < length; ++result) {
            int start = 0;
            int end = start + result;
            boolean find = false;
            HashSet<String> stores = new HashSet<>();
            for (int i = 0; i + end < length; ++i) {
                String aim = S.substring(start, end);
                if (stores.contains(aim)) continue;
                stores.add(aim);
                if (-1 == S.indexOf(aim, end + 1)) {
                    find = true;
                    break;
                }
            }
            if (find) break;
        }
        return result;
    }

    public int solution(String[] A) {
        int result = 0;
        List<HashSet<Character>> stores = new ArrayList<>();//存储所有字符
        for (String a : A) {
            HashSet<Character> store = new HashSet<>();
            for (char c : a.toCharArray()) {
                if (store.contains(c)) continue;
                store.add(c);
            }
            if (store.size() == a.length()) stores.add(store);
        }
        int length = stores.size();//多少无重复字符的string
        int[] dp = new int[length];
        for (int i = 1; i < length; ++i) {
            result = Math.max(dp(i, stores, dp, stores.get(i)), stores.get(i).size());
        }
        return result;
    }

    public boolean merge(HashSet<Character> a, HashSet<Character> b) {
        HashSet<Character> merge = new HashSet<>();
        merge.addAll(a);
        merge.addAll(b);
        return merge.size() == a.size() + b.size();
    }

    public int dp(int aim, List<HashSet<Character>> stores, int[] length, HashSet<Character> merge) {
        int result = stores.get(aim).size();
        for (int i = 0; i < aim; ++i) {
            if (merge(stores.get(aim), stores.get(i))) {
                HashSet<Character> now = new HashSet<>();
                now.addAll(stores.get(i));
                now.addAll(merge);
                result = Math.max(result, dp(i, stores, length, now));
            }
        }
        length[aim] = result;
        return result;
    }

    //8.20-1388 困难 3n披萨

    /**
     * 中间最重要的思路就是将原问题转化为在3n个数中选取不相邻的n个数的最大和
     *
     * @param slices
     * @return
     */
    public int maxSizeSlices1(int[] slices) {
        int result = 0;
        int length = slices.length;
        int n = length / 3;
        int[][] dp = new int[n][length];//存储第n层选择中选择第length个部分之后的最大值
        int[][] store = new int[length][length];//存储当前层每length中的
        for (int i = 0; i < n; ++i) {//第i层
            for (int j = 0; j < length; ++j) {//第j列
                if (i == 0) {//第一层
                    store[j] = select(slices.clone(), j);
                    dp[0][j] = slices[j];
//                    System.out.println("第0层第"+j+"列 选择后列表为"+Arrays.toString(store[j]));
                } else {//大于一层
                    int upRows = 0;
                    int selectRow = -1;
                    //找到最大的上层
                    for (int k = 0; k < length; k++) {
//                        System.out.println(store[k][j]);
                        if (store[k][j] != 0) {
                            if (upRows < dp[i - 1][k]) {
                                upRows = dp[i - 1][k];
                                selectRow = k;
                            }
                        }
                    }
                    if (selectRow == -1) {
                        dp[i][j] = 0;
                        continue;
                    }
                    store[j] = select(store[selectRow].clone(), j);
                    dp[i][j] = upRows + slices[j];
//                    System.out.println("第"+i+"层 第"+j+"列选择了上一层的第"+selectRow+"列 值为"+dp[i][j]);
//                    System.out.println(Arrays.toString(store[j]));
                }
            }
        }
        for (int i = 0; i < length; ++i) {
//            System.out.println(dp[n-1][i]);
            result = Math.max(result, dp[n - 1][i]);
        }
        return result;
    }

    public int[] select(int[] aim, int target) {
        if (target == -1) return aim;
        aim[target] = 0;
        int left = target - 1;
        while (left >= 0 && aim[left] == 0) left--;
        if (left < 0) {
            left = aim.length - 1;
            while (left >= 0 && aim[left] == 0) left--;
        }
        aim[left] = 0;

        int right = target + 1;
        while (right < aim.length && aim[right] == 0) right++;
        if (right >= aim.length) {
            right = 0;
            while (right < target && aim[right] == 0) right++;
        }
        aim[right] = 0;
        return aim;
    }

    public int maxSizeSlices(int[] slices) {
        int[] v1 = new int[slices.length - 1];
        int[] v2 = new int[slices.length - 1];
        System.arraycopy(slices, 1, v1, 0, slices.length - 1);
        System.arraycopy(slices, 0, v2, 0, slices.length - 1);
        int ans1 = calculate(v1);
        int ans2 = calculate(v2);
        return Math.max(ans1, ans2);
    }

    public int calculate(int[] slices) {
        int N = slices.length, n = (N + 1) / 3;
        int[][] dp = new int[N][n + 1];
        for (int i = 0; i < N; i++) {
            Arrays.fill(dp[i], Integer.MIN_VALUE);
        }
        dp[0][0] = 0;
        dp[0][1] = slices[0];
        dp[1][0] = 0;
        dp[1][1] = Math.max(slices[0], slices[1]);
        for (int i = 2; i < N; i++) {
            dp[i][0] = 0;
            for (int j = 1; j <= n; j++) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i - 2][j - 1] + slices[i]);
            }
        }
        return dp[N - 1][n];
    }

    //8.21
    //2337 移动片段得到字符串 总等
    public boolean canChange1(String start, String target) {
        if (!compare(start, target)) return false;
        for (int i = 0, j = 0; i < start.length() && j < target.length(); ++i, ++j) {
            //以匹配一个字符作为循环的计量维度
            while (i < start.length() && start.charAt(i) == '_') i += 1;
            while (j < target.length() && target.charAt(j) == '_') j += 1;
            if (i == start.length() || j == target.length()) break;
            if (start.charAt(i) != target.charAt(j)) return false;
            if (start.charAt(i) == 'L') {
                if (i < j) return false;
            } else {
                if (i > j) return false;
                i += 1;
                while (i < start.length() && start.charAt(i) == '_') i += 1;
                if (i <= j) return false;
                i = j;
            }
        }
        return true;
    }

    //判断两个字符串的字符个数以及次序是否匹配
    public boolean compare(String start, String target) {
        String[] startRes = start.split("_*");
        String[] targetRes = target.split("_*");
        for (int i = 0, j = 0; i < startRes.length; ++i, ++j) {
            while (startRes[i].equals("")) ++i;
            while (targetRes[j].equals("")) ++j;
            if (!startRes[i].equals(targetRes[j])) return false;
        }
        return true;
    }

    public boolean canChange(String start, String target) {
        int n = start.length();
        int i = 0, j = 0;
        while (i < n && j < n) {
            while (i < n && start.charAt(i) == '_') {
                i++;
            }
            while (j < n && target.charAt(j) == '_') {
                j++;
            }
            if (i < n && j < n) {
                if (start.charAt(i) != target.charAt(j)) {
                    return false;
                }
                char c = start.charAt(i);
                if ((c == 'L' && i < j) || (c == 'R' && i > j)) {
                    return false;
                }
                i++;
                j++;
            }
        }
        while (i < n) {
            if (start.charAt(i) != '_') {
                return false;
            }
            i++;
        }
        while (j < n) {
            if (target.charAt(j) != '_') {
                return false;
            }
            j++;
        }
        return true;
    }

    //8.22
    //849 到最近的人的最大距离 中等
    //暴力解法
    public int maxDistToClosest(int[] seats) {
        int length = seats.length;
        int result = 1;
        for (int i = 0; i < length; ++i) {
//            System.out.println("000");
            if (seats[i] == 1) continue;//当前座位有人
            int flag = 1;
            while (true) {
                if (i - flag >= 0 && seats[i - flag] == 1) break;
                if (i + flag < length && seats[i + flag] == 1) break;
                flag += 1;
//                System.out.println("当前处于"+i+"位置 flag="+flag);
            }
            result = Math.max(result, flag);
        }
        return result;
    }

    //8.23
    //1782 统计点对的数目 困难
    public int[] countPairs(int n, int[][] edges, int[] queries) {
        int[] result = new int[queries.length];
//        int[][] storeEdges = new int[n][n];
//        HashMap<Integer[], Integer> counts = new HashMap<>();
//        for(int[] edge: edges) {
//            int left = Math.min(edge[0], edge[1]) - 1;
//            int right = Math.max(edge[0], edge[1]) - 1;
//            storeEdges[left][right] += 1;
//        }
        for (int i = 0; i < queries.length; ++i) {
            for (int k = 1; k < n; ++k) {
                for (int q = k + 1; q <= n; ++q) {
//                    System.out.println(k+" "+q+" "+checkQueryCount(queries[i], edges, k, q));
                    if (checkQueryCount(queries[i], edges, k, q)) result[i] += 1;
                }
            }
        }
        return result;
    }

    public boolean checkQueryCount(int compare, int[][] storesEdges, int left, int right) {
//        int sum = storesEdges[left][right];
        int sum = 0;
        for (int[] storesEdge : storesEdges) {
//            System.out.println(left+" "+right+" "+sum);
            if (sum > compare) return true;
            int L = Math.min(storesEdge[0], storesEdge[1]);
            int R = Math.max(storesEdge[0], storesEdge[1]);
            if (L == left || L == right || R == left || R == right) sum += 1;
        }
        return sum > compare;
    }
    //疯狂超时

    /**
     * 示例解法思路：
     * 1. 设置了query[i] 表示第 i个节点有多少边；再对边数组 按照每个节点相连的边数量进行排列
     * 2. 通过将 查找大于queries[i]的节点对数量 转化为 查找节点 b相连边大于 aueries - query[a] 的问题
     * 3. 通过遍历节点 a，对于节点 b > queries - query[a] - cnt[a, b] 进行二分查找
     * 4. 关于 ab边的多次计数问题， 可以使用 x = a*n + b进行映射 将对应 cnt[a, b]边的数量转化为 cnt[x]
     */

    //8.24
    //1267 统计参与通信的服务器 中等
    public int countServers(int[][] grid) {
        int result = 0;
        //存储每一行的联通情况
        boolean[] rowCheck = new boolean[grid.length];
        //存储每一列的联通情况
        boolean[] colCheck = new boolean[grid[0].length];
        for (int i = 0; i < grid.length; ++i) {
            int countRow = 0;//行
            for (int j = 0; j < grid[0].length; ++j) {
                if (grid[i][j] == 1) countRow += 1;
                if (countRow > 1) rowCheck[i] = true;
                if (rowCheck[i]) break;
            }
        }
        for (int i = 0; i < grid[0].length; ++i) {
            int countCol = 0;//列
            for (int j = 0; j < grid.length; ++j) {
                if (grid[j][i] == 1) countCol += 1;
                if (countCol > 1) colCheck[i] = true;
                if (colCheck[i]) break;
            }
        }
        for (int i = 0; i < grid.length; ++i) {
            for (int j = 0; j < grid[0].length; ++j) {
                if (grid[i][j] == 1 && (rowCheck[i] || colCheck[j])) result += 1;
            }
        }
        return result;
    }

    //8.25
    //1448 统计二叉树中好节点的数量 中等
    public int goodNodes(TreeNode root) {
        int result;
        //前序遍历 根左右
        result = check(root, 0, root.val);
        return result;
    }

    public int check(TreeNode node, int result, int compare) {
        if (node == null) return result;
        if (node.val >= compare) {
            result += 1;
            compare = node.val;
        }
        result = check(node.left, result, compare);
        result = check(node.right, result, compare);
        return result;
    }

    //8.26
    //228 汇总区间 简单
    public List<String> summaryRanges(int[] nums) {
        if (nums.length == 0) return new ArrayList<>();
        List<String> result = new ArrayList<>();
        String now = nums[0] + "";
        if (nums.length == 1) {
            result.add(now);
            return result;
        }
        int end = nums[0];
        int start = nums[0];
        for (int i = 1; i < nums.length; ++i) {
            if (nums[i] == end + 1) {
                end = nums[i];
            } else {
                if (start == end) now = start + "";
                else now = start + "->" + end;
                result.add(now);
                start = nums[i];
                end = nums[i];
            }
        }
        if (start == end) now = start + "";
        else now = start + "->" + end;
        result.add(now);
        return result;
    }

    //169 多数元素 简单
    public int majorityElement(int[] nums) {
        int com = nums.length/2;
        Arrays.sort(nums);
        if(nums.length%2 == 0 && nums[0] == nums[com-1]) return nums[0];
        return nums[com];
    }

    //189 轮转数组 中等
    public void rotate(int[] nums, int k) {
        if(k == 0) return;
        int[] stores = new int[k];
        int length = nums.length;
        for(int i=0; i<k; ++i) {
            stores[i] = nums[length - i - 1];
        }
        for(int i=length-1; i>=0; --i) {
            if(i >= k) nums[i+1] = nums[i];
            else nums[i] = stores[i];
        }
    }

    //121 买卖股票的最佳时机 简单
    public int maxProfit(int prices[]) {
        int minprice = Integer.MAX_VALUE;
        int maxprofit = 0;
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < minprice) {
                minprice = prices[i];
            } else if (prices[i] - minprice > maxprofit) {
                maxprofit = prices[i] - minprice;
            }
        }
        return maxprofit;
    }

    //823 带因子的二叉树 -
    public int numFactoredBinaryTrees(int[] arr) {
        long result = 0;
        long[] stores = new long[arr.length];
        for(int i = 0; i<arr.length; ++i) {
            List<int[]> checks = check(arr, i);
            stores[i] = checks.size() + 1;
            for(int[] check: checks) {
                stores[i] *= stores[check[0]] + stores[check[1]];
            }
        }
        return (int)result%(1000000007);
    }
    public List<int[]> check(int[] arr, int flag) {
        if(flag == 0) return null;
        List<int[]> result = new ArrayList<>();
        int aim = arr[flag];
        HashSet<Integer> stores = new HashSet<>();
        for(int i=0;i<flag;++i) stores.add(arr[i]);
        for(int i=flag-1; i>=0; --i) {
            if(aim%arr[i] == 0) {
                int right = aim%arr[i];
                if(right == arr[i]) result.add(new int[]{right, right});
                else {
                    if(stores.contains(right)) result.add(new int[]{right, arr[i]});
                }
            }
        }
        return result;
    }

    //1654 到家的最少跳跃次数 中等
    public int minimumJumps(int[] forbidden, int a, int b, int x) {
        int result = 0;
        int[][] newF = new int[100][];
        Arrays.sort(newF, new Comparator<int[]>(){
            @Override
            public int compare(int[] o1, int[] o2) {
                if(o1[0]>o2[0]) return 1;
                if(o1 == o2) return 0;
                return -1;
            }
        });
        return result;
    }

    // 1761 一个图中联通三元组的最小度数 困难
    public int minTrioDegree(int n, int[][] edges) {
        int result = -1;
        int[] edgesSum = new int[n+1];
        int[][] stores = new int[n+1][n+1];
        for(int[] edge: edges) {
            stores[edge[0]][edge[1]] = 1;
            stores[edge[1]][edge[0]] = 1;
            edgesSum[edge[0]] += 1;
            edgesSum[edge[1]] += 1;
        }
        for(int i=1; i<=n; ++i) {
            int res = check(stores, i, n, edgesSum);
            if(-1 == res) continue;
            if(result == -1) result = res;
            else result = Math.min(result, res);
            if(result == 0) return 0;
        }
        return result;
    }
    public int check(int[][] stores, int flag, int n, int[] edgesSum) {
        List<Integer> nodes = new ArrayList<>();
        boolean check = false;
        int result = -1;
        for(int i=flag; i<=n; i++) if(stores[flag][i] == 1) nodes.add(i);
        if(nodes.size() < 2) return -1;
        for(int i=0; i<nodes.size()-1; ++i) {
            for(int j=i+1; j<nodes.size(); ++j) {
                if(stores[nodes.get(i)][nodes.get(j)] == 1) {
                    if(!check) result = edgesSum[flag]+edgesSum[nodes.get(i)]+edgesSum[nodes.get(j)] - 6;
                    check = true;
                    result = Math.min(result, edgesSum[flag]+edgesSum[nodes.get(i)]+edgesSum[nodes.get(j)] - 6);
                }
            }
        }
        if(check) return result;
        return -1;
    }

    //2240 买钢笔和铅笔的方案数 中等
    public long waysToBuyPensPencils(int total, int cost1, int cost2) {
        long result = 0;
        int max = Math.max(cost1, cost2);
        int min = Math.min(cost1, cost2);
        for(int i=0; i<=total/max; i++) {
            result += (total - (long) max *i) / min + 1;
            // System.out.println(result);
        }
        return result;
    }
}