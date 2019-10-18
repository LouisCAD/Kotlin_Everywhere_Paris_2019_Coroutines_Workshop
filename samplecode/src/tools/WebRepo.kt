package com.workshop.tools

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object WebRepo {
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://localhost:8080")
        .client(
            OkHttpClient.Builder()
                .readTimeout(12, TimeUnit.SECONDS)
                .build())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
}