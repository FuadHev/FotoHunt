package com.fuadhev.fotohunt.common.downloader

interface Downloader {
    fun downloadFile(url:String):Long
}