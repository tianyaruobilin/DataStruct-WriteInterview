import service.MyService;
import service.impl.MyServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class App {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("请输入字典对象，以逗号分隔");
        Scanner scanner = new Scanner(System.in);
        String inputS = scanner.nextLine();
        MyService myService=new MyServiceImpl();
        Map<String, Set<String>> map = myService.parseS2Dict(inputS);
        System.out.println("转化后的字典如下");
        for (Map.Entry<String, Set<String>> stringSetEntry : map.entrySet()) {
            String key = stringSetEntry.getKey();
            Set<String> value = stringSetEntry.getValue();
            System.out.println("key:\t" + key + "-----------------\tvalue:" + value);
        }
        System.out.println("===================================================");
        System.out.println("请输入需要匹配的字符串L");
        String inputL = scanner.nextLine();
        List<List<String>> lists = myService.parseL(inputL);
        System.out.println("转化后的字符串L如下");
        System.out.println(lists);

        System.out.println("===================================================");
        System.out.println("匹配后的结果如下");
        Set<Set<String>> list = myService.matchProcess(lists, map);
        for (Set<String> strings : list) {
            System.out.println(strings);
        }
    }
}
