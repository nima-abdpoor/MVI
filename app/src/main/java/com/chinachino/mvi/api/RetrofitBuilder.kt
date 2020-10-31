package com.chinachino.mvi.api

import com.chinachino.mvi.utils.LiveDataCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder{
    private const val BASE_URL = "https://open-api.xyz/"

    val retrofitBuilder : Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
    }
    val apiService : APIService by lazy {
        retrofitBuilder.build()
            .create(APIService::class.java)
    }
}