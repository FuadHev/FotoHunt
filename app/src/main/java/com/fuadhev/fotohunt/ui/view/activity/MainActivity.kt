package com.fuadhev.fotohunt.ui.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.fuadhev.fotohunt.R
import com.fuadhev.fotohunt.common.broadcast.DownloadCompletedReciever
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        sendBroadcast(Intent("android.intent.action.DOWNLOAD_COMPLETE"))
    }
}