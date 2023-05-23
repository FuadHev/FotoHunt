package com.fuadhev.fotohunt.di

import com.fuadhev.fotohunt.common.Constant
import com.fuadhev.fotohunt.network.WebApiService
import com.fuadhev.fotohunt.repo.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun providesRepository(webApiService: WebApiService): Repository {
        return Repository(webApiService)
    }


    @Provides
    @Singleton
    fun providesWebServiceApi(retrofit: Retrofit):WebApiService{
        return retrofit.create(WebApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit():Retrofit{
           val loggingInterceptor= HttpLoggingInterceptor()
        loggingInterceptor.level= HttpLoggingInterceptor.Level.BODY

        val clientBuilder= OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        return Retrofit.Builder().baseUrl(Constant.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(clientBuilder)
            .build()
    }

}