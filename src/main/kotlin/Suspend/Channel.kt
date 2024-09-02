package Suspend

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import java.io.BufferedReader
import java.io.File

suspend fun main(): Unit = coroutineScope {
    val bufferedReader: BufferedReader = File("Мартышка и очки.txt").bufferedReader()
    val inputString = bufferedReader.use { it.readText() }
    val text = Strogate(inputString)

    val listResult = mutableListOf<String>()
    val channelLine = Channel<String>()
    val channelSend = launch {
        getList(text.text, channelLine)
    }
    var stringResult = ""
    val channelGet = launch {
        //println(channelLine.receive())
        for (line in channelLine) {
            //println(line)
            //stringResult+=line
            listResult.add(line)
        }
    }

    joinAll(channelSend, channelGet)
    println(listResult)
    for (line in listResult) {
        stringResult += line
    }
    println(stringResult)//Выводит только последнюю строку, не ясна причина
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

suspend fun getList(text: String, channel: SendChannel<String>): Job = coroutineScope {
    //val result = Channel<String>()
    val lines = text.split("\n")
    launch {
        for (line in lines) {
            delay(10L)
            //println(line)
            channel.send(line)
        }
        channel.close()
    }
}