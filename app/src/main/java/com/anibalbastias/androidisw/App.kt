package com.anibalbastias.androidisw

import android.app.Application
import com.anibalbastias.androidisw.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                appModule
            )
        }
    }
}