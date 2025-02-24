package com.example.jetfitnessapp

import android.app.Application
import com.example.jetfitnessapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {

    override fun onCreate() {

        super.onCreate()

        startKoin {

            androidContext( this@MyApp )
            modules( appModule )

        }

    }

}