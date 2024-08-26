package Suspend

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

suspend fun main() {
    programBuyProduct()
    runLazy()
}

suspend fun programBuyProduct() {
    println("Программа покупки товаров")
    delay(1000L)
    println("Купить товар?\n1.Да\n2.Нет")
    var choise = readln()
    if (choise == "1") {
        buyProduct()
    } else if (choise == "2") {
        notBuyProduct()
    } else {
        println("Ошибка!!!")
    }
    goodbuy()
}

suspend fun goodbuy() = coroutineScope {
    launch {
        println("До свидания")
    }
}

suspend fun notBuyProduct() = coroutineScope {
    launch {
        println("Отмена покупки...")
        delay(2000L)
        println("Покупка отменена.")
    }
}

suspend fun buyProduct() = coroutineScope {
    launch {
        println("Оплата продукта началась.")
        delay(2000L)
        println("Сканирование и обработка...")
        delay(2000L)
        println("Покупка оплачена.")
    }
}

suspend fun runLazy() = coroutineScope{
    println("Начало программы")
    launch {
        var i=0
        while (i<4){
            delay(1000L)
            if (i++==2){
                println("Произошел ленивый запуск")
                CoroutineStart.LAZY
                delay(1000L)
            }
            println(i)
        }
    }.join()
    println("Программа завершена")
}