#!/usr/bin/env groovy
// 单行注释
/* 多行注释 */
/** 文档注释 */
import java.sql.Date as SQLDate         // as 关键字用于取别名


/******************************************************** String ****************************************************/
/** String Summary
StringType              StringSyntax            Interpolated            Multiline           Escape character
Single-quoted           '…'                     N                       N                   \
Triple-single-quoted    '''…'''                 N                       N                   \
Double-quoted           "…"                     Y                       N                   \
Triple-double-quoted    """…"""                 Y                       Y                   \
Slashy                  /…/                     Y                       Y                   \
Dollar slashy           $/…/$                   Y                       Y                   $
*/
def str1 = 'qwer'
def str2 = "qwer"
def str3 = '''qwer
1234'''
def str4 = """qwer
1234"""
// 最后的反斜杠表示不插入 换行符 \n, 表示 当前行和下一行同属于一行
def str4 = """qwer\                   
1234"""
def str5 = /qwer \/1234/              // 主要用于正则表达式，这样正则表达式中的 \ 字符，在代码里面就不用写成 \\\\ 四个反斜杠了
def str6 = $/qwer 1234/$

/******************************************************** Character ****************************************************/
/** Groovy 中没有 char 类型，但是有 3 中方法，把 String 类型转成 Char 类型 */
def c = 'A'
def c = 'A' as char
def c = (char)'A'

/******************************************************** Number ****************************************************/    
/** groovy 会自动将声明的数字放到对应的类型中，一般建议只使用以下两种 */
BigInteger bi =  1_000_006g
BigDecimal bd =  3.456g

/******************************************************** Array ****************************************************/
String[] arrStr = ['Ananas', 'Banana', 'Kiwi']
def numArr = [1, 2, 3] as int[]
Integer[][] matrix2 = [[1, 2], [3, 4]]

/******************************************************** List ****************************************************/ 
/**   */
def list = ['a','b','c','d']
list[0]         // == 'a'
list[1, 3]      // == ['b','d']
list[-1]        // 取倒数第一个
list << 'e'     // 往 list 追加一个 'e'
list[2..4]      // == ['b','d','e']
def multi = [[0, 1], [2, 3]]            // 定义多维 List
assert multi[1][0] == 2

/******************************************************** Map ****************************************************/
/** todo  */
def map = [:]       // 定义 空 map
def map = [name:"Haku", age: 22, "string key": true]
println map.name
println map."string key"
println map.["string key"]
println "${map.name} is ${map.22} years old"

/******************************************************** Closure ****************************************************/
/** 一个 Closure 是一个 groovy.lang.Closure 的实例  */
{ [closureParameters -> ] statements }
def res = { a, b=2 -> a-b}      // b=2 表示 b 默认值为 2
res(3,2)
res.call(3,2)

/******************************************************** Method ****************************************************/

/******************************************************** Script ****************************************************/
/** 如果一个文件中，有任何代码不在 class 类中，那么这个文件就是一个 script，
script 会被编译成一个类，假设类名为 Suppose，这个类会继承 groovy.lang.Script 
所有脚本都会被 复制到一个 run 方法中，
所有文件中定义的方法，都会被设置为 Suppose 类的方法
且 Suppose 类中有一个 main 方法，可以执行，相当于 java 中的 public static void main(String[]) 方法
*/

/******************************************************** Programming Style ***************************************/
class ProgrammingStyle {
    /** No semicolons: groovy 编程不需要在语句后面加 ; 结尾 */
    String name
    /** Dynamic type: 使用 def 关键字定义标识符，不需要指定标识符类型，def 定义的标识符为 Object 类型 */
    def age
    
    /** Defualt method access modifier is public: 类方法默认访问修饰符为 public */
    /** Defualt method parameter: 默认方法参数 */
    def func(param1, String param2 = 'default', Class param3){
        /** Optional return keyword: 方法体中最后一个表达式就是方法的返回值，不需要写 return 关键字 */
        'return value'
    }
}

println 'Programming Style Demo Start'

/** 自动生成构造函数 及 字段赋值 */
def ps1 = new ProgrammingStyle()
def ps2 = new ProgrammingStyle(name: "Obelix", age: 23)         

/** Omitting parentheses: 顶层表达式方法调用可以省略括号，但是无参方法调用必须带括号 */
/** public class Class: 类名可以直接当作标识符使用，作用相当于 类名.class */
ps1.func 1, 'p2', Map

/** 字段可直接赋值，会自动调用 getter setter 方法 */
ps1.name = 'kasei'

/** with() 方法可以指定字段和方法调用的上下文 */
ps1.with {
    name = 'haku'
    age = 22
    func 3, 'p2', List
}

/** tap() 方法可以指定字段和方法调用的上下文，且默认返回调用者，可以使用 with(true) 替代 tap */
def ps3 = new ProgrammingStyle().tap {
    name = 'miku'
}
/** ?. 避免 NullPointException ，如果上一级为 null 则立即返回 null */
println ps1?.name

/** ps1 为 null, void, =0, ps1.length=0 都会判定为 false */
assert ps1
def result = ps?:"Defualt Value"

def range1 = 1...5      // 1 到 5 包括 5
def range2 = 1...>5     // 1 到 5 不包括 5

println 'Programming Style Demo End'




