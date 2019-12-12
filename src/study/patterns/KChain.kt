package study.patterns

import java.lang.IllegalArgumentException

/**
 * @author Lee
 * @Title: {Kotlin中的责任链模式}
 * @Description:{借助PartialFunction类的封装，不仅大幅减少了程序的代码量，而且在构建责任链是，可以用orElse获得更好的语法表达}
 * @date 2019/12/12 10:00
 */
class KChain{

    /**
     * 偏函数类型
     * @param definetAt 校验函数
     * @param f 处理函数
     */
    class PartialFunction<in P1,out R>(private val definetAt:(P1) ->Boolean,private val f:(P1)->R):(P1)->R{

        override fun invoke(p1: P1): R {
            //definetAt对输出参数p1进行有效性检测，校验通过执行f函数，反之抛出异常
            if(definetAt(p1)){
                return f(p1)
            }else{
                throw  IllegalArgumentException("Value: (&p1) isn`t supported by this function")
            }
        }
        fun isDefinedAt(p1:P1)=definetAt(p1)
    }

    //infix关键字然orElse成为一个中缀函数
    infix fun <P1,R>PartialFunction<P1,R>.orElse(that:PartialFunction<P1,R>):PartialFunction<P1,R>{
        return PartialFunction({this.isDefinedAt(it)||that.isDefinedAt(it)}){
            when{
                this.isDefinedAt(it)-> this(it)
                else -> that(it)
            }
        }
    }

    //以下测试类
    data class ApplyEvent(val money:Int,val title:String)

    interface ApplyHandler{
        val successor:ApplyHandler?
        fun handleEvent(event: ApplyEvent)
    }

    /**
     * 使用PartialFunction定义GroupLeader
     */
    val groupLeader ={val definetAt:(ApplyEvent)->Boolean ={it.money<=200}
        val handler:(ApplyEvent) ->Unit ={ println("GroupLeader handled application: ${it.title}")}
        PartialFunction(definetAt,handler)
    }()

    val president ={val definetAt:(ApplyEvent)->Boolean ={it.money<=500}
        val handler:(ApplyEvent) ->Unit ={ println("President handled application: ${it.title}")}
        PartialFunction(definetAt,handler)
    }()

    val college ={val definetAt:(ApplyEvent)->Boolean ={true}
        val handler:(ApplyEvent) ->Unit ={ when{
            it.money >1000 -> println("College: This application is refused.")
            else -> println("college handled application: ${it.title}")
        }}
        PartialFunction(definetAt,handler)
    }()

    //使用orElse构建责任链模式和PartialFunction类型的中缀表达式applyChain
    val applyChain =groupLeader orElse  president orElse college

    fun main(args: Array<String>){
        //运行测试用例
        applyChain(ApplyEvent(600,"hold a debate match"))
    }



}
