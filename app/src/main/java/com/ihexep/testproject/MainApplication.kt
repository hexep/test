package com.ihexep.testproject

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import com.ihexep.testproject.di.AppModule
import com.ihexep.testproject.di.DomainModule
import com.ihexep.testproject.di.NetworkModule

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(AppModule, NetworkModule, DomainModule)
        }
    }
}