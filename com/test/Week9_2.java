package com.test;

import java.util.*;

public class Week9_2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = Integer.parseInt(in.nextLine());
        for(int i=0; i<n; ++i) {
            int now = Integer.parseInt(in.nextLine())/3;
            if(now < 3) {
                System.out.println(0);
                continue;
            }
            System.out.println((now-2)*(now-1)/2);
        }
    }
    public static void main2(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] num = in.nextLine().split(" ");
        int length = Integer.parseInt(num[0]);//aim长度
        int n = Integer.parseInt(num[1]);//敏感词数量
        String aim = in.nextLine();
        HashSet<String> stores = new HashSet<>();//存储敏感词
        while (in.hasNextLine()) stores.add(in.nextLine());
        int appearance = 0;
        for (String store : stores) {
            int lenOfWord = store.length();
            if (aim.length() < lenOfWord) continue;
            for (int i = 0; i <= aim.length() - lenOfWord; ++i) {
                int flag = 0;
                while (flag < lenOfWord &&
                        store.charAt(flag) == aim.charAt(i + flag)) flag += 1;
                if (flag == lenOfWord) {
                    appearance += 1;
                }
            }
        }
        System.out.println(appearance);
    }


    //通过对于敏感词较多的情况进行处理：如果有敏感词互相包含，则将短敏感词合并到长敏感词中
//通过HashMap进行存储
    public static HashMap<String, Integer> deal(HashSet<String> stores) {
        // Arrays.sort(stores);
        String[] newStores = (String[])stores.toArray();
        Arrays.sort(newStores);
        HashMap<String, Integer> result = new HashMap<>();
        String now = "";
        return result;
    }

    public static void m2_1(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = Integer.parseInt(in.nextLine());
        String[] nums = in.nextLine().split(" ");
//        System.out.println(Arrays.toString(nums));
        int a = -1000000000;
        int b = 1000000002;
        String result = "Yes";
        for (String num : nums) {
            int now = Integer.parseInt(num);
//            System.out.println("a="+a+" b="+b+" now="+now);
            if (a >= now || now - a >= b) {
                result = "No";
                break;
            }
            b = now - a;
            a = now;
        }
        System.out.println(result);
    }

    public static void m2_2(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = Integer.parseInt(in.nextLine());
        String aim = "";
        while (in.hasNextLine()) aim = aim.concat(in.nextLine());
        char[] meituan = new char[]{'m', 'e','i','t','u','a','n'};
        int flag = 0;
        for(char now: aim.toCharArray()) {
            if(flag == 7) break;
            if(now == meituan[flag]) flag += 1;
        }
        System.out.println(flag==7?"YES":"NO");
    }

    //没思路捏
    public static void m2_3(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = Integer.parseInt(in.nextLine());
        String[] Strings = in.nextLine().split(" ");
        int[] nums = new int[n];
        int i=0;
        while (in.hasNextInt()) {
            nums[i] = in.nextInt();
            i += 1;
        }
//        for(int i=0;i<nums.length; ++i) nums[i] = Integer.parseInt(Strings[i]);
        int aim = nums[0];
        Arrays.sort(nums);
        int result = 0;
        int mid = aim;
        while (mid < nums[n-1]) {
            mid *= 2;
            result += 1;
        }
        for(int j=n-1; j>=0; i--) {

        }
    }

    public static void m2_4(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] nm = in.nextLine().split(" ");
        int n = Integer.parseInt(nm[0]);
        int m = Integer.parseInt(nm[1]);
        int[] nums = new int[n];
        int flag=0;
        while (in.hasNextInt()) nums[flag++] = in.nextInt();
        Arrays.sort(nums);
        int size = n - m;
        long result = 0L;
        result +=  find(0, nums, new ArrayList<>(), size);
        System.out.println(result%(1000000007));
    }

    public static int find(int flag, int[] nums, List<Integer> now, int size){
        System.out.println("flag="+flag+" now="+now+" size="+size);
        if(size == now.size()) return 1;
        if(flag == nums.length || nums.length-flag < size-now.size()) return 0;
        int result = 0;
        for(int i=flag; i<nums.length; ++i) {
            if(now.isEmpty()) {
                List<Integer> nextNew = new ArrayList<>();
                nextNew.add(nums[i]);
                result +=  find(i+1, nums, nextNew, size);
                continue;
            }
            if(check(nums[i], now)) {
                List<Integer> newNow = new ArrayList<>(now);
                newNow.add(nums[i]);
                result += find(i+1, nums, newNow, size);
            }
        }
        return result;
    }

    public static boolean check(int aim, List<Integer> now) {
        for(int nowNum: now) if(aim%nowNum != 0) return false;
        return true;
    }

    public static void m2_5(String[] args) {

        Scanner in = new Scanner(System.in);
        int n = Integer.parseInt(in.nextLine());
        int flag = 0;
        int[] nums = new int[n];
        while (in.hasNextInt()) nums[flag++] = in.nextInt();
        HashSet<Integer> kind = new HashSet<>();//总共多少种糖
        HashMap<Integer, Integer> stores = new HashMap<>();
        for(int num: nums) {
            if(kind.contains(num)) stores.put(num, stores.get(num)+1);
            else {
                kind.add(num);
                stores.put(num, 1);
            }
        }
        int[] sizes = new int[kind.size()];
        flag = 0;
        for(Integer nowKind: kind) {
            sizes[flag] = stores.get(nowKind);
            flag+=1;
        }
        Arrays.sort(sizes);
        int result = 0;
        for(int num: sizes) {
            if(num>2) {
                num -= 2;
                result+=1;
            }
        }
        for(int i=0; i<sizes.length-1; ++i) {
            for(int j=i+1; j<sizes.length; ++j) {
                if(sizes[i]>0 && sizes[j]>0) {
                    sizes[i] -= 1;
                    sizes[j] -= 1;
                    result += 1;
                }
            }
        }
        for(int num:nums) result += num/2;
        System.out.println(result);
    }

    public int eliminateMaximum(int[] dist, int[] speed) {
        int result=0;
        boolean flag = true;
        int[] kills = new int[dist.length];//存储当前怪物是否被杀
        while (flag) {
            for(int i=0; i<dist.length; ++i) {
                if(kills[i] != 0 && dist[i]<=0) {
                    flag = false;
                    break;
                }
            }
            if(!flag || result==dist.length) break;
            result+=1;
            int kill = -1;
            for(int i=0; i<dist.length; ++i) {
                if(kills[i] == -1) continue;
                dist[i] -= speed[i];
                kill = kill == -1 || dist[i] < dist[kill] ? i : kill;
            }
            if(kill != -1) kills[kill] = -1;
        }
        return result;
    }

    /**
     * 使用贪心算法
     * @param dist
     * @param speed
     * @return
     */
    public int eliminateMaximumSolution(int[] dist, int[] speed) {
        int n = dist.length;
        int[] arrivalTimes = new int[n];
        for (int i = 0; i < n; i++) {
            arrivalTimes[i] = (dist[i] - 1) / speed[i] + 1;
        }
        Arrays.sort(arrivalTimes);
        for (int i = 0; i < n; i++) {
            if (arrivalTimes[i] <= i) {
                return i;
            }
        }
        return n;
    }
}
