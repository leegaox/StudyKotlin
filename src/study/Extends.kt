/**
 * 扩展方法
 */
class Extends {

    companion object{
        fun MutableList<Int>.exchange(fromIndex: Int, toIndex: Int) {
            val tmp = this[fromIndex]
            this[fromIndex] = this[toIndex]
            this[toIndex] = tmp
        }
    }



    fun abc(){
        val list = mutableListOf(1,2,4)
        list.exchange(1,2)
    }
}

fun MutableList<Int>.exchange(fromIndex: Int, toIndex: Int) {
    val tmp = this[fromIndex]
    this[fromIndex] = this[toIndex]
    this[toIndex] = tmp
}