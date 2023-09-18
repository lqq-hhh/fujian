import java.util.*;
public class Week9_18 {
    static int aim;
    static int num;
    static int[] stores;
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        aim = Integer.parseInt(in.nextLine());//目标数量
        num = Integer.parseInt(in.nextLine());//商品总数
        stores = new int[num];
        for (int i = 0; i < num; ++i) stores[i] = in.nextInt();
        int result = 0;
        for (int i = 0; i < num; ++i) {
            result += find(i, stores[i]);
        }
        System.out.println(result);
    }
    public static int find(int flag, int now) {
        if (now == aim) return 1;
        if (now > aim) return 0;
        int result = 0;
        for (int i = flag; i < stores.length; ++i) {
            if (flag == i) continue;
            if (aim - now >= stores[i]) result += find(i + 1, now + stores[i]);
        }
        return result;
    }
}
