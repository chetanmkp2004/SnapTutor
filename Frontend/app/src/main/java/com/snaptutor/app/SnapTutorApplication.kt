package com.snaptutor.app

import android.app.Application
import com.snaptutor.app.core.di.AppContainer
import com.snaptutor.app.core.di.DefaultAppContainer

class SnapTutorApplication : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
