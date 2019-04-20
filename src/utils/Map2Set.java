package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 这是一个自定义的工具类
 */
public class Map2Set {

    /**
     * 转化为不重复的带key的Set
     * @return 不重复的带key的Set
     */
    public static List<Set<String>> parse(Map<String, Set<String>> dict) {
        List<Set<String>> list=new ArrayList<>();
        for (String s : dict.keySet()) {
            Set<String> strings = dict.get(s);
            strings.add(s);
            list.add(strings);
        }
        return list;
    }

}
