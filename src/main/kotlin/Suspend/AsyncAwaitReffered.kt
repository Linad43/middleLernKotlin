package Suspend


import kotlinx.coroutines.delay

suspend fun main() {
    val list1 = getRandomList(10)
    unpack(list1)
    val list2 = getRandomList(4)
    unpack(list2)
    val list3 = concatenate(list1, list2)
    unpack(list3.toList())
}

fun getRandomList(size: Int): List<String> {
    val result = mutableListOf<String>()
    for(i in 1..size){
        result.add(getRandomString((3..8).random()))
    }
    return result
}

fun getRandomString(size: Int): String {
    val chars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..size).map { chars.random() }.joinToString("")
}

suspend fun <T> unpack(list:List<T>) {
    var count = 0
    for (element in list){
        print("$element\t");
        delay(1000L)
        count++
    }
    println()
    println("Количество элементов в коллекции: $count")
}

fun <T, V> concatenate(list1: List<T>, list2: List<V>): Pair<Int, MutableList<String>> {
    val result:MutableList<String> = list1.map { it.toString() }.toMutableList()
    result.addAll(list2.map { it.toString() })
    return Pair(list1.size + list2.size, result)
}