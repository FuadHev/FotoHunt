package com.fuadhev.fotohunt.model

data class FotoResponse(
    val hits: List<Hit>,
    val total: Int,
    val totalHits: Int
)