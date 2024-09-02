package Suspend

import kotlinx.coroutines.*

suspend fun main(): Unit = coroutineScope {
    val manager = PersonManager()

    println("Программа работы с базой данных сотрудников")
    delay(1000L)
    var flagFirst = true
    while (true) {
        println("Добавить сотрудника?")
        println("1. Да")
        if (!flagFirst) {
            println("2. Нет. Прочитать базу данных")
        } else {
            println("2. Нет")
        }
        val choise = readln()
        when (choise) {
            "1" -> {
                println("Введите имя сотрудника:")
                val name = readln()
                var salary: Double
                do {
                    try {
                        println("Введите зарплату сотрудника:")
                        salary = readln().toDouble()
                        break
                    } catch (e: Exception) {
                        println("Неверные данные, попробуйте снова")
                    }
                } while (true)
                manager.addPerson(Person(name, salary))
                flagFirst = false
            }

            else -> {
                if (!flagFirst) {
                    println("Чтение базы данных")
                    manager.readPersonList()
                } else {
                    println("База данных пуста")
                }
                break
            }
        }
    }
    //Вот тут не могу понять как закрыть корутину по вводу нуля
    if (!flagFirst) {//проверка что бы список не был пустым
        val job = launch(Dispatchers.Default) {//Запуск добавления пароля и чтения данных
            println("launch job")
            manager.addPassword()
            manager.readDataPersonList()
        }
        val cancelJob = launch(Dispatchers.Default) {//это по сути слушатель нажатия
            println("launch cancelJob")
            var buf: String
            while (job.isActive) {
                buf = readln()
                if (buf == "0") {
                    job.cancel()
                    println("job cancel")
                    break
                }
            }
            println("cancelJob cancel")
        }
        /*val checkJob =
            launch(Dispatchers.Default) {//эта создана для закрытия слушателя если ничего не нажато и чтение данных закончено
                println("launch checkJob")
                job.join()
                println("==job cancel==")
                cancelJob.cancel()
                println("==cancelJob cancel==")
                //this.cancel()
            }*/
        //joinAll(job, cancelJob, checkJob)
        joinAll(job, cancelJob)
        println("cancelAll")
    }

    println("Завершение полной работы")
}

data class Person(
    val name: String,
    val salary: Double
) {
    override fun toString(): String {
        return "Сотрудник $name зарплата $salary"
    }
}

class PersonManager() {
    private val personList = mutableListOf<Person>()
    private val resultList = mutableMapOf<Person, Int>()

    fun addPerson(person: Person) {
        personList.add(person)
    }

    fun readPersonList() {
        personList.forEach {
            println(it)
        }
    }

    suspend fun addPassword() {
        personList.forEach {
            var password = 0
            while (password < Math.pow(10.0, 5.0)) {
                password *= 10
                password += (Math.random() * 10).toInt()
            }
            delay(500L)
            resultList[it] = password
            println("Create password for ${it.name}.")
        }
    }

    suspend fun readDataPersonList() {
        delay(1000L)
        resultList.forEach {
            println("${it.key}; пароль ${it.value}")
        }
        println("All done")
    }
}