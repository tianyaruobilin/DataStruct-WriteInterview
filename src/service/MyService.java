package service;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MyService {

    /**
     *
     * 将输入S转字典
     * @param inputS 输入
     * @return 字典
     */
    public Map<String, Set<String>> parseS2Dict(String inputS);

    /**
     * 将输入 L 转化为 List<List<String>>，其中 List<String> 为每一个输入 L 元素，因为输入的时候 L 中带空格，
     * @param inputL 输入
     * @return List<List<String>>
     */
    public List<List<String>> parseL(String inputL);

    /**
     * 匹配过程
     * @param needMatch 需要匹配对象
     * @param dict 字典对象
     * @return 匹配后的同义词集合
     */
    public Set<Set<String>> matchProcess(List<List<String>> needMatch,Map<String,Set<String>> dict);
}
