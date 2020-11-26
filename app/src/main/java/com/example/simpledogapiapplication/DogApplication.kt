package com.example.simpledogapiapplication

import android.app.Application
import com.facebook.stetho.Stetho

class DogApplication: Application() {
    lateinit var serviceLocator: ServiceLocator

    override fun onCreate() {
        super.onCreate()
        serviceLocator = ServiceLocator(applicationContext)
        Stetho.initializeWithDefaults(this)
    }
}