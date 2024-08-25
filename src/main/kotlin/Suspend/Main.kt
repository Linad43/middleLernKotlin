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
        Weather("oneCity", (1..10).random(), ((1..10000).random()) / 10.0),
        Weather("twoCity", (1..10).random(), ((1..10000).random()) / 10.0),
        Weather("treeCity", (1..10).random(), ((1..10000).random()) / 10.0),
        Weather("fourCity", (1..10).random(), ((1..10000).random()) / 10.0)
    )
    val randoms = listOf(
        (1..1000).random(),
        (1..1000).random(),
        (1..1000).random(),
        (1..1000).random(),
        (1..1000).random(),
        (1..1000).random(),
        (1..1000).random(),
        (1..1000).random(),
        (1..1000).random(),
        (1..1000).random()
    )
    val tasks = listOf(
        launch {
            initList(persons)
        },
        launch {
            initList(weathers)
        },
        launch {
            initList(randoms)
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
    val city: String,
    val description: Int,
    val temp: Double
) {
    override fun toString(): String {
        return "city = $city, description = $description, temp = $temp"
    }
}

suspend fun <T> initList(list: List<T>): List<T> {
    for (element in list) {
        delay(1000L)
        println("downloading: $element")
    }
    println("downloading complete")
    return list
}