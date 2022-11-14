package com.example.masterdetailflowkotlintest.modules

import com.example.masterdetailflowkotlintest.BuildConfig
import com.example.masterdetailflowkotlintest.api.GeocodingHelper
import com.example.masterdetailflowkotlintest.api.GeocodingHelperImpl
import com.example.masterdetailflowkotlintest.api.GeocodingService
import com.example.masterdetailflowkotlintest.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton



@InstallIn(SingletonComponent::class)
@Module
open class Retrofit {

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL


    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG){
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }else{
        OkHttpClient
            .Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL:String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideGeocodingService(retrofit: Retrofit): GeocodingService = retrofit.create(GeocodingService::class.java)

    @Provides
    @Singleton
    fun provideGeocodingHelper(apiHelper: GeocodingHelperImpl): GeocodingHelper = apiHelper


}