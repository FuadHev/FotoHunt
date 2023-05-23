package com.fuadhev.fotohunt.root

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class HiltApp:Application() {

    companion object {
        const val CHANNEL_ID = "DownloadChannel"
        const val CHANNEL_NAME = "Download Channel"
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
            val manager=getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(
//                CHANNEL_ID,
//                CHANNEL_NAME,
//                NotificationManager.IMPORTANCE_DEFAULT
//            )
//            val manager=getSystemService(NotificationManager::class.java)
//            manager.createNotificationChannel(channel)
//        }
    }
}