package com.example.furniq.app

import android.app.Application
import android.os.StrictMode
import com.example.furniq.di.helperModule
import com.example.furniq.di.networkModule
import com.example.furniq.di.repositoryModule
import com.example.furniq.di.viewModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App:Application(){
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@App)
            modules(networkModule, repositoryModule, viewModule, helperModule)
        }


    }
}