package com.workshop

import com.workshop.tools.WebRepo
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.system.exitProcess
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

val apiContext = Executors.newFixedThreadPool(2).asCoroutineDispatcher()
@ExperimentalTime
fun main(): Unit = runBlocking {
    println("starting client")


    val service = WebRepo.retrofit.create(CustomService::class.java)

//    service.suspendCalls()

//    service.synchronousCalls()

//    service.asynchronousCalls()

//    service.customCoroutine()
    service.customCancellableCoroutine()

    exitProcess(0)
}

private suspend fun CustomService.customCoroutine() {
    println(" let's go!")
    val result = retrofitCoroutine { hellow() }
    println(" hello coroutine ${result.hello}")
}

private suspend fun CustomService.customCancellableCoroutine() {
    println(" let's go!")
    val result = retrofitCancellableCoroutine { hellow() }
    println(" hello cancellable coroutine ${result.hello}")
}

suspend inline fun <reified T> retrofitCancellableCoroutine(
    crossinline request: () -> Call<T>
) : T = suspendCancellableCoroutine { continuation ->
    request.invoke().run {
        continuation.invokeOnCancellation { cancel() }
        enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                response.body()?.let { continuation.resume(it) } ?: continuation.resumeWithException(IllegalStateException())
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                continuation.resumeWithException(t)
            }
        })
    }
}

suspend inline fun <reified T> retrofitCoroutine(
    crossinline request: () -> Call<T>
) : T = suspendCoroutine { continuation ->
    request.invoke().run {
        enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                response.body()?.let { continuation.resume(it) } ?: continuation.resumeWithException(IllegalStateException())
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                continuation.resumeWithException(t)
            }
        })
    }
}

private suspend fun CustomService.asynchronousCalls() {
    val flow = callbackFlow {
        val cb = object : Callback<JsonSampleClass> {
            override fun onFailure(call: Call<JsonSampleClass>, t: Throwable) {
                close()
            }

            override fun onResponse(call: Call<JsonSampleClass>, response: Response<JsonSampleClass>) {
                if (response.isSuccessful) offer(response.body())
                close()
            }
        }
        println(" let's go!")
        hellow().enqueue(cb)
        awaitClose {  }
    }
    println(" hello cb ${flow.single()?.hello}")
}

@ExperimentalTime
suspend fun CustomService.synchronousCalls() {
    println(" let's go!")
    val duration = measureTime {
        val msg = withContext(apiContext) { hellow().execute().body()?.hello }
        println(" hello slow $msg")
    }
    println("took ${duration.inSeconds}")
}

@ExperimentalTime
private suspend fun CustomService.suspendCalls() {
    println(" let's go!")
    var duration = measureTime { println(" hello fast ${hello().hello}") }
    println("took ${duration.inSeconds}")
    duration = measureTime { println(" hello slow ${hellong().hello}") }
    println("took ${duration.inSeconds}")
}

data class JsonSampleClass(val hello: String)

interface CustomService {
    @GET("json/hello")
    suspend fun hello(): JsonSampleClass
    @GET("json/hellong")
    suspend fun hellong(): JsonSampleClass
    @GET("json/hellong")
    fun hellow(): Call<JsonSampleClass>
}