package org.example.Suspend

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

suspend fun main() = coroutineScope {
    println("Начало работы программы")
    val persons = listOf(
        Person("one", 21),
        Person("two", 18),
        Person("tree", 19),
        Person("four", 19)
    )
    val weathers = listOf(
        Weather((1..100).random(), (1..100).random(), (1..100).random()),
        Weather((1..100).random(), (1..100).random(), (1..100).random()),
        Weather((1..100).random(), (1..100).random(), (1..100).random()),
        Weather((1..100).random(), (1..100).random(), (1..100).random()),
        Weather((1..100).random(), (1..100).random(), (1..100).random()),
        Weather((1..100).random(), (1..100).random(), (1..100).random()),
        Weather((1..100).random(), (1..100).random(), (1..100).random()),
        Weather((1..100).random(), (1..100).random(), (1..100).random()),
        Weather((1..100).random(), (1..100).random(), (1..100).random()),
        Weather((1..100).random(), (1..100).random(), (1..100).random()),
        Weather((1..100).random(), (1..100).random(), (1..100).random()),
        Weather((1..100).random(), (1..100).random(), (1..100).random())
    )
    val tasks = listOf(
    launch{
        initList(persons)
    },
    launch{
        initList(weathers)
    })
    tasks.joinAll()
    println("Программа завершена")
}

class Person(
    val name: String,
    val age: Int
) {
    override fun toString(): String {
        return "$name ($age)"
    }
}

class Weather(
    val city: Int,
    val description: Int,
    val temp: Int
){
    override fun toString(): String {
        return "city = $city, description = $description, temp = $temp"
    }
}

suspend fun <T> initList(list:List<T>):List<T>{
    for(element in list){
        delay(1000L)
        println("downloading: $element")
    }
    println("downloading complete")
    return list
}