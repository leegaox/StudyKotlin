/**
 * @author Lee
 * @Title: {高阶函数简化策略模式}
 * @Description:{本质上，策略模式做的事情是将不同的策略进行独立封装，与类在逻辑思维解耦。然后根据不同的上下文切换选择不同的策略，然后用类对象进行调用}
 * @date 2019/12/12 9:58
 */
package study.patterns

class KStrategy{


fun main() {
    //初始化Swimmer时，可以用函数引用的语法传达构造参数
    val weekendShaw = Swimmer(::freestyle)
    weekendShaw.swim()
    val weekdaysShaw = Swimmer(breaststroke)
    weekdaysShaw.swim()
}

/**
 * 策略算法都封装成了一个个函数
 */

//把函数用val声明成lambda表达式
val breaststroke: () -> Unit = { println("I am breaststroking") }

fun backstroke() {
    println("I am backstroke")
}

fun freestyle() {
    println("I am freestyle")
}

/**
 *
 */
class Swimmer(val swimming: () -> Unit) {
    fun swim() {
        swimming()
    }
}
}