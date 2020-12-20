package com.example.wheretoeat.api

import com.example.wheretoeat.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
    val api by lazy {
        retrofit.create(RestaurantApiService::class.java)
    }

}