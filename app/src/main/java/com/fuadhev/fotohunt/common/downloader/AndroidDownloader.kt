package com.fuadhev.fotohunt.common.downloader

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri

class AndroidDownloader(private val context: Context): Downloader {

    private val donwloadManager=context.getSystemService(DownloadManager::class.java)
    override fun downloadFile(url: String): Long {
        val request= DownloadManager.Request(url.toUri())
            .setMimeType("image/jpeg")
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
//            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle("image.jpeg")
            .addRequestHeader("Authorization","Bearer <token>")
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"image.jpg")
        return  donwloadManager.enqueue(request)
    }

}