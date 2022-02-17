package com.zialab.data.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

interface DispatcherProvider {
    fun io(): CoroutineDispatcher = Dispatchers.IO
}

class DefaultDispatcherProvider @Inject constructor() : DispatcherProvider