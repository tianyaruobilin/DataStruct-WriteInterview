# DataStruct-WriteInterview

用最擅长的语言编写一个函数/方法F(S,L),实现以下功能：
输入一份同义词列表S，和一系列空格分割的字符串L,将L中的同义的字符串分组输出。
S中每相邻两个词标识它们为同义词，同义词之间有传递性
S中的词不会有空格
例：
S=[
"一百","100","壹佰","100","壹佰","一OO","AAA","AAa"
]
L=[
"一百 元","满分 一百","100 元","一OO 元","AAA BB","AAa BB","壹佰元","一OO元"
]

输出：
"一百 元","100 元","一OO 元"
"AAA BB","AAa BB",
"满分 一百",
"壹佰元"
"一OO元"
每行代表统一的一组字符串
分组的顺序和组件舒徐可任意排列。
注：字符串中子串同意不代表重个字符串同义，例如，假设"贰"和"二"同义，
不代表可以退出"贰十"和"二十"同义。

一百,100,壹佰,100,壹佰,一OO,AAA,AAa
一百 元,满分 一百,100 元,一OO 元,AAA BB,AAa BB,壹佰元,一OO元
