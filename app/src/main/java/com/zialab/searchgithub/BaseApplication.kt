package com.zialab.searchgithub

import android.content.Context
import androidx.lifecycle.LifecycleObserver
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication: DaggerApp(), LifecycleObserver {
    private lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        if (application == null) {
            application = this
        }
    }

    companion object {
        var application: BaseApplication? = null
            private set

        val context: Context
            get() = application!!.applicationContext
    }

    fun appComp() = appComponent
}