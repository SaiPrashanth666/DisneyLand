package com.spcoding.foodiebuddy

import android.app.Application
import com.spcoding.foodiebuddy.di.initKoin
import org.koin.android.ext.koin.androidContext

class PracticeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@PracticeApplication)
        }
    }
}