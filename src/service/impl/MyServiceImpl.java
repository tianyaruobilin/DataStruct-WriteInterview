package service.impl;

import service.MyService;
import utils.ListSplit;
import utils.Map2Set;

import java.util.*;

public class MyServiceImpl implements MyService {

    /**
     * 这里为简单计，设输入的S对象为只有逗号分割的，方便分割
     * @param inputS 输入
     * @return 字典对象
     */
    @Override
    public Map<String, Set<String>> parseS2Dict(String inputS) {
        //输入转集合
        List<String> stringList = Arrays.asList(inputS.split(","));

        //确保stringList输入个数为偶数
        if ((stringList.size()) % 2 == 0) {

            //创建字典对象
            Map<String, Set<String>> dict = new HashMap<>();

            for (int i = 0; i < stringList.size(); i++) {
                //将每一个输入转化为字典项
                if ((i + 1) % 2 != 0) {
                    //先判断是否存在key为stringList<i>的map对象
                    if (dict.containsKey(stringList.get(i))) {
                        //如果包含,获取该key的同义串
                        Set<String> strings = dict.get(stringList.get(i));
                        //创建key为stringlist.get(i)的对象
                        strings.add(stringList.get(i + 1));
                        //更新map数据
                        dict.put(stringList.get(i), strings);
                    } else {
                        Set<String> list = new HashSet<>();
                        list.add(stringList.get(i + 1));
                        dict.put(stringList.get(i), list);
                    }
                } else {
                    //如果是第奇数个对象
                    //先判断是否存在key为stringList<i>的map对象
                    if (dict.containsKey(stringList.get(i))) {
                        //如果包含,获取该key的同义串
                        Set<String> strings = dict.get(stringList.get(i));
                        //创建key为stringlist.get(i)的对象
                        strings.add(stringList.get(i - 1));
                        //更新map数据
                        dict.put(stringList.get(i), strings);
                    } else {
                        Set<String> list = new HashSet<>();
                        list.add(stringList.get(i - 1));
                        dict.put(stringList.get(i), list);
                    }

                }
            }
            //到这里形成了粗略的字典，但是互相之间还没有传导性，接下来需要通过递归，达成同义词之间的传导性
            /*=============================================================================================*/
            //被维护的一个新的遍历集合
            Set<String> traversed = new HashSet<>();

            //需要返回的字典对象

            //先深拷贝一个dict,作为过程字典
//            Map<String, Set<String>> resultDict = new HashMap<>(dict);
            Map<String, Set<String>> resultDict = new HashMap<>(dict);

            /*
             * 根据该同义词串去map中根据key查找，查找出来的所有value,继续遍历，判断如果在traversed中没有，就查到value中对应的map,然后递归
             * 所以递归规则如下
             *      输入:travsed(被维护的递归),Map<String,Set<String>>(字典),Map<String,Set<String>>(用于接收返回结果的Set)
             *      输出:Map<String,Set<String>> 获得了传导性的新字典
             *      递归结束条件:当value值在travsed存在时且该key有同义词时，不递归
             *  ==>递归方式如下：
             */
            /*=============================================================================================*/


            Map<String, Set<String>> processDict = secondaryTreate(traversed, resultDict, dict);

            Map<String, Set<String>> complete = complete(processDict);

            return complete;

        } else {
            throw new RuntimeException("请检查输入个数");
        }
    }

    /**
     * 将过程字典转换成结果字典
     *
     * @param processDict 过程字典
     * @return 结果字典
     */
    private Map<String, Set<String>> complete(Map<String, Set<String>> processDict) {
        List<Set<String>> parse = Map2Set.parse(processDict);   //深克隆一个processDict为parse
        for (String s : processDict.keySet()) {
            for (Set<String> strings : parse) {
                if (strings.contains(s)) {
                    Set<String> strings1 = processDict.get(s);
                    strings.addAll(strings1);
                    processDict.put(s, strings);
                }
            }
        }
        return processDict;
    }

    /**
     * 获取具有传导性的字典
     *
     * @param traversed  自维护的一个遍历集合
     * @param resultDict 自维护的一个过程字典,同时最后用于存储具有传导性的字典
     * @param dict       传递过来的不具有传导性的字典
     * @return 具有传导性的字典
     */
    private Map<String, Set<String>> secondaryTreate(Set<String> traversed, Map<String, Set<String>> resultDict, Map<String, Set<String>> dict) {

        for (Map.Entry<String, Set<String>> stringSetEntry : dict.entrySet()) {

            String key = stringSetEntry.getKey();
            if (!traversed.contains(key)) {

                //先维护起来
                traversed.add(key);

                Set<String> value = stringSetEntry.getValue();

                for (String s : value) {
                    if (!traversed.contains(s)) {
                        if (resultDict.get(s) == null || resultDict.get(s).isEmpty()) {
                            Set<String> set = new HashSet<>();
                            set.add(s);
                            resultDict.put(key, set);
                            traversed.add(s);
                        } else {
                            Set<String> strings = resultDict.get(s);
                            strings.add(s);
                            resultDict.put(key, strings);
                            traversed.add(s);
                            secondaryTreate(traversed, resultDict, dict);
                        }
                    }

                }


            }

        }
        return resultDict;
    }


    @Override
    public List<List<String>> parseL(String inputL) {
        String[] list = inputL.split(",");
        List<List<String>> stringL = new ArrayList<>();
        for (String s : list) {
            List<String> list1 = Arrays.asList(s.split(" "));
            stringL.add(list1);
        }
        return stringL;
    }

    /**
     * 到这里输入已经大概都捋清楚了，现在我们要做的才是正事，开始匹配逻辑
     *
     * @param needMatch 需要匹配对象
     * @param dict      字典对象
     * @return 需要输出的结果
     */
    @Override
    public Set<Set<String>> matchProcess(List<List<String>> needMatch, Map<String, Set<String>> dict) {
        Set<Set<String>> matchResult = new HashSet<>();
        for (List<String> match : needMatch) {
            Set<String> strings1 = new HashSet<>();

            if (match.size() == 1) {
                for (String s : match) {
                    Set<String> strings = dict.get(s);
                    for (String match1 : match) {
                        if (strings == null || strings.isEmpty()) {
                            strings1.add(s);
                            matchResult.add(strings1);
                        } else {
                            if (strings.contains(match1)) {
                                strings1.add(match1);
                                strings1.add(s);
                                matchResult.add(strings1);
                            }
                        }
                    }
                }
            } else {
                for (String s : match) {
                    //这是每一个词里面的一个部分
                    Set<String> list = dict.get(s);//获取同义词
                    if (list == null || list.isEmpty()) {
                        //如果list为空
                        continue;
                    } else {
                        //不为空
                        for (String s1 : list) {
                            List<String> list1 = new ArrayList<>(match);
                            list1.set(list1.indexOf(s), s1);
                            for (List<String> strings : needMatch) {
                                if (ListSplit.addSplitor2String(strings).equals(ListSplit.addSplitor2String(list1))) {
                                    strings1.add(ListSplit.addSplitor2String(strings));
                                    strings1.add(ListSplit.addSplitor2String(match));
                                    matchResult.add(strings1);
                                }
                            }
                        }
                    }
                }

            }
        }
        return OutPutDistinct(matchResult);
    }

    /**
     * 去除Set<Set<String>>中的重复对象
     *
     * @return 绝无重复的匹配结果
     */
    private Set<Set<String>> OutPutDistinct(Set<Set<String>> outSet) {

        Map<String, Set<String>> distinctSetString = new HashMap<>();

        Set<Set<String>> sets = new HashSet<>();

        for (Set<String> set : outSet) {
            for (Set<String> strings : outSet) {
                if (strings.toString().equals(set.toString())) {
                    distinctSetString.put(strings.toString(), strings);
                }
            }
        }

        for (Set<String> set : outSet) {
            sets.add(distinctSetString.getOrDefault(set.toString(), set));
        }

        return sets;
    }
}
