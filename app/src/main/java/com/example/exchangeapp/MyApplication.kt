package com.example.exchangeapp

import android.app.Application
import com.example.exchangeapp.DI.appModule
import com.example.exchangeapp.DI.retrofitModule
import com.example.exchangeapp.DI.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(listOf(
                appModule,
                retrofitModule,
                viewModelModule
            ))
        }
    }
}