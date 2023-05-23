package com.fuadhev.fotohunt.network

import com.fuadhev.fotohunt.common.Constant.API_KEY
import com.fuadhev.fotohunt.model.FotoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WebApiService {


    @GET("/api/")
    suspend fun searchFotos(@Query("q") imageName:String,
                         @Query("image_type")image_type:String,
                            @Query("page") page:Int=1,
                         @Query("key") apiKey : String = API_KEY):Response<FotoResponse>

}