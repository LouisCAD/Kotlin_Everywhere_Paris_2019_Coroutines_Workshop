package com.workshop

import com.workshop.tools.AsynchronousApi
import com.workshop.tools.ClassicalCallback
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {

    val api = AsynchronousApi()
    val flow = callbackFlow {
        val listener = object : ClassicalCallback {
            override fun onValueReceived(value: Int) {
                offer(value)
            }
            override fun onFinished() {
                close()
            }
        }
        api.listener = listener
        api.fibo()
        awaitClose { api.listener = null }
    }

    flow.map { it*it }
        .collect {
        println("received: $it")
    }
}