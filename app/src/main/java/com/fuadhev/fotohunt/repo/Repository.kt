package com.fuadhev.fotohunt.repo

import com.fuadhev.fotohunt.model.FotoResponse
import com.fuadhev.fotohunt.network.WebApiService
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: WebApiService) {


    suspend fun searchFotos(image_name:String, image_type:String,page:Int): Response<FotoResponse> {
        return apiService.searchFotos(image_name,image_type,page)
    }
}