package com.zialab.data.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseUrl

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ZiaLabServices

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OkhttpByKey
