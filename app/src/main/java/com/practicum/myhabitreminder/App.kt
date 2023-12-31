package com.practicum.myhabitreminder

import android.app.Application
import com.practicum.myhabitreminder.common.di.ApiModule
import com.practicum.myhabitreminder.common.di.authModule
import com.practicum.myhabitreminder.common.di.dbModule
import com.practicum.myhabitreminder.common.di.habitModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(
                listOf(
                    dbModule,
                    habitModule,
                    authModule,
                    ApiModule
                )
            )
        }
    }
}