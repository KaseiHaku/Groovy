#!/usr/bin/env groovy
// 单行注释
/* 多行注释 */
/** 文档注释 */
println "Hello from the shebang line"



/** todo String */
def str1 = 'qwer'
def str2 = "qwer"
def str3 = '''qwer
1234'''
def str4 = """qwer
1234"""
def str5 = /qwer 1234/
def str6 = $/qwer 1234/$

/** todo List */
def list = ['a','b','c','d']
list[0]         // == 'a'
list[1, 3]      // == ['b','d']
list << 'e'     // 往 list 追加一个 'e'
list[2..4]      // == ['b','d','e']

/** todo Map */
def map = [name:"Haku", age: 22, "string key": true]
println map.name
println map."string key"
println map.["string key"]
println "${map.name} is ${map.22} years old"

/** todo Closure */
{ [closureParameters -> ] statements }
def res = { a, b=2 -> a-b}      // b=2 表示 b 默认值为 2
res(3,2)
res.call(3,2)

