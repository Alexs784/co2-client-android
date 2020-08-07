package com.alessandro.co2client

import android.app.Application
import com.alessandro.core.di.networkModule
import com.alessandro.core.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Co2Application : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Co2Application)
            modules(networkModule, repositoryModule)
        }
    }
}