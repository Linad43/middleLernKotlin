package Suspend

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import java.io.BufferedReader
import java.io.File
import kotlin.time.measureTime

suspend fun main(): Unit = coroutineScope {
    val bufferedReader: BufferedReader = File("Мартышка и очки.txt").bufferedReader()
    val inputString = bufferedReader.use { it.readText() }
    val text = Strogate(inputString)

    val listResult = mutableListOf<String>()
    val time = measureTime {
        coroutineScope {
            val channelSend = getList(text.text)
            var stringResult = ""
            val channelGet = launch {
                //var stringResult = ""
                //println(channelLine.receive())
                for (line in channelSend) {
                    //println("line: $line")
                    //println(line)
                    //stringResult+=channelLine.receive()
                    stringResult += line
                    listResult.add(line)
                    //println("listResult:$stringResult")
                }
                /*channelSend.consumeEach {
                    //println("$it")
                    stringResult+=it
                    listResult.add(it)
                }*/
            }
            val channelMod = modifiedList(channelSend)
            joinAll(channelGet)
            //пробовал по разному, но в переменную записывается только последняя строка
            println("listResult:\n${listResult}")
            println("stringResult:\n$stringResult")

            channelMod.consumeEach {
                println("Полученные данные: $it")
            }//Эта вообще не работает, не понятно почему
        }
    }
    println("Затраченное время $time мс")
    /*for (line in listResult) {
        stringResult += line
    }*/
    //println(stringResult)//Выводит только последнюю строку, не ясна причина

    /*launch {
        for (line in text.text.split("\n")) {
            delay(10L)
            //println(line)
            listLine.send(line)
        }
        //getList(text.text)

    }
    repeat(text.text.split("\n").size) {
        val line = listLine.receive()
        println(line)
    }*/

}

class Strogate(val text: String) {

}

suspend fun CoroutineScope.modifiedList(channel: ReceiveChannel<String>): ReceiveChannel<String> = produce {
    channel.consumeEach {
        it.first().toString().uppercase()
    }
}

suspend fun CoroutineScope.getList(text: String): ReceiveChannel<String> = produce {
    val lines = text.split("\n")
    for (line in lines) {
        delay(10L)
        //println(line)
        send(line/*.replace("\n", "")*/)
    }
    close()
}

/*suspend fun CoroutineScope.getStringList(channel: ReceiveChannel<String>): ReceiveChannel<String> = produce {
    for (line in channel) {
        //println("line: $line")
        //println(line)
        //stringResult+=channelLine.receive()
        stringResult += line
        listResult.add(line)
        println("listResult:$stringResult")
    }
}*/