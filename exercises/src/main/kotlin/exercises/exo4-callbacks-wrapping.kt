package exercises

import kotlin.concurrent.thread

suspend fun playWithCallbacksWrapping() {
    TODO("Write a suspending function that returns when the callback is called, " +
            "and call that suspending function here.")
}

private fun exampleCallback(cb: ()-> Unit) {
    thread {
        Thread.sleep(300)
        cb()
    }
}
