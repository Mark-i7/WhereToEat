package com.example.wheretoeat.api

import com.example.wheretoeat.models.Restaurant
import com.example.wheretoeat.models.RestaurantByCity
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call
import retrofit2.Response

interface RestaurantApiService  {

    @GET("restaurants")
    suspend fun getRestaurantsByCity(@Query("city")city:String): RestaurantByCity

}