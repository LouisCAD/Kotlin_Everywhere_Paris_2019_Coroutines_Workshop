package com.workshop.tools

import kotlinx.coroutines.delay

class AsynchronousApi {

    var listener : ClassicalCallback? = null
    // Simulate an async API
    suspend fun fibo() = listener?.let {
        var a = 0
        var b = 1
        println("sent: ${a+b}")
        it.onValueReceived(1)
        while (b < 100) {
            it.onValueReceived(a + b)
            println("sent: ${a+b}")
            val tmp = a + b
            a = b
            b = tmp
            delay(500)
        }
        it.onFinished()
    }
}

interface ClassicalCallback {
    fun onValueReceived(value : Int)
    fun onFinished()
}
//    val fibonacciSeq = sequence {
//        var a = 0
//        var b = 1
//
//        yield(1)
//
//        while (true) {
//            yield(a + b)
//
//            val tmp = a + b
//            a = b
//            b = tmp
//            delay(100)
//        }
//    }
//    val fib = produce<Int> {
//        var a = 0
//        var b = 1
//
//        send(1)
//
//        while (true) {
//            send(a + b)
//
//            val tmp = a + b
//            a = b
//            b = tmp
//            delay(100)
//        }
//    }
