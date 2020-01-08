#!/usr/bin/env groovy


import groovy.lang.Closure
import java.sql.Date as SQLDate         // as 关键字用于取别名



/******************************************************** Variable ****************************************************/
/** TODO 变量定义 */
String var0             // 具体类型的变量定义
def var1                // 不定类型的变量定义
var3                    // Script 中字段定义

/** TODO 变量赋值 */
var4 = 123              // 一次赋值一个变量
def (int var5, String var6, var7) = [1, 'q', false]     // 一次赋值多个变量

def str0 = '123'
def str1 = 'qwe $str0 \\ r'
def str2 = "qwe $str0 \\ r"
def str5 = /qwe $str0 \/ r/              // 主要用于正则表达式，这样正则表达式中的 \ 字符，在代码里面就不用写成 \\\\ 四个反斜杠了
def str6 = $/qwe $str0 $$ r/$
def str7 = """
           |<root>
           |    <level>3</level>
           |</root>
           """

BigInteger bi =  1_000_006g
BigDecimal bd =  3.456g

String[] arrStr = ['Ananas', 'Banana', 'Kiwi']  // 一维数组
def numArr = [1, 2, 3] as int[]                 // def 定义指定类型的数组
Integer[][] matrix2 = [[1, 2], [3, 4]]          // 二维数组


def list = ['a','b','c','d']
def multiList = [[0, 1], [2, 3]]            // 定义多维 List
list << 'e'     // 往 list 追加一个 'e'

def map1 = [:]       // 定义 空 map
def map2 = [name:"Haku", age: 22, "string key": true]
def map3 = [(str0): "Haku"]  // 以变量作为 map 的 key

/** TODO 变量取值 */
println var4
println arrStr[0]
println matrix2[0][1]

println list[0]         // == 'a'
println list[1, 3]      // == ['b','d']
println list[-1]        // 取倒数第一个
println list[2..4]      // == ['b','d','e']
println multiList[1][0]

println map2.name
println map2."string key"
println map3[str0]
println "${map2.name} is ${map2.age} years old"

/******************************************************** Operators ****************************************************/
println 1  + 2
println 4  - 3
println 3  * 5
println 3  / 2
println 10 % 3
println 2 ** 3
println +3
println -4
println -(-1)
def intVar = 3
println intVar++
println ++intVar
println intVar--
println --intVar


int a = 0b00101010
int b = 0b00001000
int mask = 0b11111111
assert ((a ^ a) & mask) == 0b00000000
assert ((a ^ b) & mask) == 0b00100010
assert ((~a) & mask)    == 0b11010101

def displayName = user.name ? user.name : 'Anonymous'
def displayName = user.name ?: 'Anonymous'        // 上面语句的简写 变量值 为 null, void, =0, ps1.length=0 都会判定为 false


def name = person?.name     // 不会报 NullPointerException 直接返回 null
def name = person.@name     // .@ 操作符会强制直接使用 name 属性，而不是调用 getter setter 方法

def funcPointer = ''.&toUpperCase   // C 函数指针


def regexp = ~/foo/         // 正则表达时字面量， java.util.regex.Pattern
def matcher = "some text to match" =~ /match/       // java.util.regex.Matcher
def isMatch = "some text to match" ==~ /match/      // 直接返回是否匹配的结果

def persons = []
def personNames = persons*.name
def args = [4,5,6]
function(*args)
def list = [1,2,3,*args,6]

def m1 = [c:3, d:4]
def map = [a:1, b:2, *:m1]

def range = 1..5
def range = 1..<5
def range = 'a'..'g'

// compareTo
(1 <=> 1)

def list = [0,1,2,3,4]
list[0..2] = [6,6,6]


def list = ['Grace','Rob','Emmy']
assert ('Emmy' in list)


def list1 = ['Groovy 1.8','Groovy 2.0','Groovy 2.3']
def list2 = ['Groovy 1.8','Groovy 2.0','Groovy 2.3']
assert list1 == list2
assert !list1.is(list2)


Integer x = 123
String s = (String) x

List<String> strings = new LinkedList<>()

def mc = new MyCallable()
assert mc.call(2) == 4
assert mc(2) == 4

<<  左移
>>  右移
>>>   无符号右移


/******************************************************** Method ****************************************************/
/** TODO 方法定义 */
class MethodSyntax {

    /* TODO 方法返回值的简化 */
    def String return1(){ return 'aaaa' }
    def String return2(){ 'aaaa' } // 省略 return ，默认返回最后一行的值

    void method1(){}
    void method2(Map<String, ?> map, Integer number, String str='default'){}


}
/** TODO 方法调用
 * Omitting parentheses: 顶层表达式方法调用可以省略括号，但是无参方法调用必须带括号
 * public class Class: 类名可以直接当作标识符使用，作用相当于 类名.class
 * */
new MethodSyntax().method2([key1: 'a', key2: 2], 3)    // 严格按照遵循参数格式及顺序
new MethodSyntax().method2(key1: 'a', key2: 2, 3)      // 省略 Map 参数的中括号
new MethodSyntax().method2(key1: 'a', 3, key2: 2)      // 调换参数顺序，注意:调换只有在 Map 类型的参数是方法的第一个参数时有效
new MethodSyntax().method2 3, key1: 'a', key2: 2       // 省略方法调用的小括号


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



/******************************************************** Class ***************************************/
class GroovyClass{
    @PackageScope String name   // 定义包访问权限
    def func(param1, String param2 = 'default', Class param3){
        'return value'
    }
}


/** with() 方法可以指定字段和方法调用的上下文 */
new GroovyClass().with {
    name = 'haku'
    age = 22
    func 3, 'p2', List
}

/** tap() 方法可以指定字段和方法调用的上下文，且默认返回调用者，可以使用 with(true) 替代 tap */
def ps3 = new GroovyClass().tap {
    name = 'miku'
}




/******************************************************** Script ****************************************************/
/** 如果一个文件中，有任何代码不在 class 类中，那么这个文件就是一个 script，
 script 会被编译成一个类，假设类名为 Suppose，这个类会继承 groovy.lang.Script
 所有脚本都会被 复制到一个 run 方法中，
 所有文件中定义的方法，都会被设置为 Suppose 类的方法
 且 Suppose 类中有一个 main 方法，可以执行，相当于 java 中的 public static void main(String[]) 方法
 */




