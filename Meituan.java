import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Meituan {
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
}
