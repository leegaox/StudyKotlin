/**
 * @author Lee
 * @Title: {Kotlin中的观察者模式}
 * @Description:{}
 * @date 2019/12/12 10:00
 */
package study.patterns
import kotlin.properties.Delegates

class KObserver{



fun main(args:Array<String>){
    val su =StockUpdate()
    val sd =StockDisplay()
    su.listeners.add(sd)
    su.price=100
    su.price =98
    su.value=1
    su.value=-1
}


/**
 *  通过额外定义一个接口将上涨和下跌的不同响应逻辑封装成接口方法，实现逻辑解耦
 */
interface StockUpdateListener {
    fun onRise(price: Int)
    fun onFall(price: Int)
}

/**
 * 观察者
 *
 */
class StockDisplay : StockUpdateListener {
    override fun onRise(price: Int) {
        println("The latest stock price has risen to ${price}")
    }

    override fun onFall(price: Int) {
        println("The latest stock price has fell to ${price}")
    }

}

/**
 * 可被观察的发布者类
 */
class StockUpdate {

     val listeners = mutableSetOf<StockUpdateListener>()

    //使用可被观察的“委托属性”
     var price: Int by Delegates.observable(0) { _, old, new ->
        listeners.forEach {
            if (new > old) it.onRise(price) else it.onFall(price)
        }
    }

    //Delegates.vetoable 委托属性在被赋值生效之前提前进行截获，然后判断是否否定它
    var value :Int by Delegates.vetoable(0){prop,old, new ->
        new >0
    }


}

}