package com.fuadhev.fotohunt.common.broadcast

import android.Manifest
import android.app.DownloadManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.fuadhev.fotohunt.R
import com.fuadhev.fotohunt.root.HiltApp.Companion.CHANNEL_ID

class DownloadCompletedReciever :BroadcastReceiver(){

    private lateinit var builder: NotificationCompat.Builder
    private lateinit var notificationManager: NotificationManagerCompat

    override fun onReceive(context: Context?, intent: Intent?) {


        if (intent?.action == DownloadManager.ACTION_DOWNLOAD_COMPLETE) {

            val notificatiContext=context ?: return
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L)
            if (id != -1L) {
                Log.e("downloadComlete", "Completed $id")
                showNotification(notificatiContext,"Download Completed", "Image downloaded successfully", true)
            }else{
                showNotification(notificatiContext,"Download No Completed", "Image downloaded no successfully", true)
            }
        }
    }

    fun showNotification(context: Context, title: String, content: String, isComplete: Boolean) {
        val notificationId = 1
        notificationManager = NotificationManagerCompat.from(context)
        builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)
            .setOngoing(false)

        if(isComplete){
            val notificationIntent = Intent(DownloadManager.ACTION_VIEW_DOWNLOADS)
            val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0)
            builder.setContentIntent(pendingIntent)

        }

        // no needed this codes I only try progress bar
        /*if (progress != null && !isComplete) {
            builder.setProgress(100, progress, false)
                .setOngoing(true)
                .build()
        } else {
            builder.setProgress(0, 0, false)
            builder.setContentText(content)
            builder.setAutoCancel(true)
            builder.setOngoing(false)
                .build()
        }*/

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManager.notify(notificationId, builder.build())
    }

}