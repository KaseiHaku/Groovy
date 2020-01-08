#!/usr/bin/env groovy


import groovy.lang.Closure
import java.sql.Date as SQLDate         // as 关键字用于取别名



/******************************************************** Variable ****************************************************/
/** 变量赋值 */
def var0 = 'str'
def (int var1, String var2, var3) = [1, 'q', false]

/******************************************************** String ****************************************************/
/** String Summary
 * StringType              StringSyntax            Interpolated            Multiline           Escape character
 * Single-quoted           '…'                     N                       N                   \
 * Triple-single-quoted    '''…'''                 N                       N                   \
 * Double-quoted           "…"                     Y                       N                   \
 * Triple-double-quoted    """…"""                 Y                       Y                   \
 * Slashy                  /…/                     Y                       Y                   \
 * Dollar slashy           $/…/$                   Y                       Y                   $
 * */
def str1 = 'qwe \n r'
def str2 = "qwe \n r"
def str5 = /qwe \/ r/              // 主要用于正则表达式，这样正则表达式中的 \ 字符，在代码里面就不用写成 \\\\ 四个反斜杠了
def str6 = $/qwe \n $$ r/$


/******************************************************** Number ****************************************************/
/** groovy 会自动将声明的数字放到对应的类型中，一般建议只使用以下两种 */
BigInteger bi =  1_000_006g
BigDecimal bd =  3.456g

/******************************************************** Array ****************************************************/
String[] arrStr = ['Ananas', 'Banana', 'Kiwi']  // 一维数组
def numArr = [1, 2, 3] as int[]                 // def 定义指定类型的数组
Integer[][] matrix2 = [[1, 2], [3, 4]]          // 二维数组


/******************************************************** List ****************************************************/
def list = ['a','b','c','d']
def multiList = [[0, 1], [2, 3]]            // 定义多维 List

void showListUsage(){
    println list[0]         // == 'a'
    println list[1, 3]      // == ['b','d']
    println list[-1]        // 取倒数第一个
    println list << 'e'     // 往 list 追加一个 'e'
    println list[2..4]      // == ['b','d','e']
    println multiList[1][0]
}


/******************************************************** Map ****************************************************/
def emptyMap = [:]       // 定义 空 map
def ordinarayMap = [name:"Haku", age: 22, "string key": true]
def kasei = 'string key'
def varKeyMap = [(kasei): "Haku"]  // 以变量作为 map 的 key

void showMapUsage(){
    println map.name
    println map."string key"
    println map[kasei]
    println "${map.name} is ${map.age} years old"
}

/******************************************************** Method ****************************************************/

class MethodSyntax {
    void method1(){}

    void method2(Map<String, ?> map){}

    // TODO 方法返回值的简化
    def String return1(){ return 'aaaa' }
    def String return2(){ 'aaaa' } // 省略 return ，默认返回最后一行的值
}

// TODO 方法参数为 Map 的简化
new MethodSyntax().method2([key1: 'a', key2: 2])    // 初始
new MethodSyntax().method2(key1: 'a', key2: 2)      // 省略中括号
new MethodSyntax().method2 key1: 'a', key2: 2       // 省略小括号


// TODO 函数指针
def funcPointer = new MethodSyntax().&method1   // 相当于 C 语言的函数指针
funcPointer()



/******************************************************** Closure ****************************************************/
/** 一个 Closure 是一个 groovy.lang.Closure 的实例
 每一个 Closure 都有一个隐式参数 it，跟 java 里面的 this 指针一样，都是第一个参数
 Closure<String> 表示 Closure 的返回值是一个 String 实例
 */
def closure1 = { -> println '无参 closure '}   // 定义无参闭包必须使用箭头
def closure2 = { println "Hello $it" }  // 包含一个隐藏参数 it，指向调用者的实例
def closure3 = { item++ }   // 包含一个参数的闭包
def closure4 = { def it, String x, int y=4  ->   // 参数全部显式声明
    x = x.trim()
    println "x=${x};y=${y}"
    x
}

closure4(3,2)           // 调用 Closure 方式 1
closure4.call(3,2)      // 调用 Closure 方式 2



/** Closure delegate strategy
 * 闭包中包含以下 3 个概念：
 *      this:       对应闭包定义代码所在类的实例，无论当前闭包A是否定义在另一个闭包B内
 *      owner:      对应闭包定义代码所在的直接外围对象，如果闭包A定义在闭包B内，闭包B又定义在类C内，那么 owner 对应的时闭包B的实例
 *      delegate:   对应闭包调用时，手动设置的执行实例，默认设置为 owner
 *
 * 闭包的委托策略：当一个变量名/方法名 aa 没有在闭包域内声明时，由委托策略来决定 aa 来自于哪里，那么默认使用 delegate.aa 调用
 *      Closure.OWNER_FIRST: 优先使用 owner 中的属性，owner 中没有再使用 delegate 中的属性
 *      Closure.DELEGATE_FIRST: 优先使用 delegate 中的属性，delegate 中没有再使用 owner 中的属性
 *      Closure.OWNER_ONLY: 只使用 owner 中的属性
 *      Closure.DELEGATE_ONLY: 只使用 delegate 中的属性
 *      Closure.TO_SELF: 当自己实现 groovy.lang.Closure 接口时，可以自定义闭包策略
 * */
class ClosureDelegateStrategyDemo {
    void run(){
        def closureB = {
            this                    // 永远指向 ClosureDelegateStrategyDemo 的实例
            owner                   // 因为闭包嵌套，所以 owner 也指向 ClosureDelegateStrategyDemo 的实例
            def closureA = {
                this                    // 永远指向 ClosureDelegateStrategyDemo 的实例
                owner                   // 因为有闭包嵌套，所以 owner 指向 closureB 指向的实例
                delegate
            }
        }
    }
}



/** 闭包 柯里化（Curry）：将多个参数的方法，拆分成一个参数的多个方法 */






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
ps1.@name = 'kasei'  // .@ 操作符会强制直接使用 name 属性，而不是调用 getter setter 方法



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

def range1 = 1..5      // 1 到 5 包括 5
def range2 = 1 ..> 5     // 1 到 5 不包括 5

println 'Programming Style Demo End'


/******************************************************** Class ***************************************/
class GroovyClass{
    @PackageScope String name   // 定义包访问权限
}




