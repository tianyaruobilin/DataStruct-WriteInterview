package utils;

import java.util.List;

public class ListSplit {

    private static final String SPLIT = " ";

    public static String addSplitor2String(List<String> list){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            stringBuilder.append(list.get(i));
            if (i!=list.size()-1){
                stringBuilder.append(SPLIT);
            }
        }
        return stringBuilder.toString();
    }
}
