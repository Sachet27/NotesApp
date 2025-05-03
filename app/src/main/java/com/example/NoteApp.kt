package com.example

import android.app.Application
import com.example.di.appModule
import com.example.di.notificationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NoteApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Notification.createNotificationChannel(this)
        startKoin {
            androidContext(this@NoteApp)
            androidLogger()
            modules(appModule, notificationModule)
        }
    }
}