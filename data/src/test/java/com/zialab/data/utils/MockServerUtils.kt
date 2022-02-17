package com.zialab.data.utils

import com.zialab.data.api.ZiaLabApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun createInternalService(baseUrl: String): ZiaLabApiService {
    val okHttpClient = OkHttpClient
        .Builder()
        .build()
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .build().create(ZiaLabApiService::class.java)
}