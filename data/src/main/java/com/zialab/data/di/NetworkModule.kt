@file:Suppress("ObjectLiteralToLambda")

package com.zialab.data.di

import com.example.data.di.BaseUrl
import com.example.data.di.OkhttpInterceptor
import com.example.data.di.ZiaLabServices
import com.zialab.data.api.ZiaLabApiService
import com.zialab.data.util.DefaultDispatcherProvider
import com.zialab.data.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val TIME_OUT_SECONDS = 1000L

    @BaseUrl
    @Provides
    fun provideBaseUrl(): String = "https://api.github.com/"

    @ZiaLabServices
    @Provides
    fun provideApiMethods(
        @BaseUrl baseUrl: String,
        @OkhttpInterceptor okHttpClient: OkHttpClient
    ): ZiaLabApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .callFactory { request ->
                Timber.d("url: ${request.url}")
                okHttpClient.newCall(request)
            }
            .build()
            .create(ZiaLabApiService::class.java)

        return retrofit
    }

    @OkhttpInterceptor
    @Provides
    fun providesOkhttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {

        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient().newBuilder()

        httpClient.apply {
            readTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
            connectTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
            writeTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
            retryOnConnectionFailure(false)
        }

        httpClient.addNetworkInterceptor(loggingInterceptor)

        return httpClient.build()
    }

    @Provides
    fun provideLogInterceptor() = HttpLoggingInterceptor()

    @Provides
    fun provideDispatcherProvider(): DispatcherProvider {
        return DefaultDispatcherProvider()
    }
}