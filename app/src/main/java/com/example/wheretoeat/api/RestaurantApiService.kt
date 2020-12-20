package com.example.wheretoeat.api

import com.example.wheretoeat.models.CitiesResponse
import com.example.wheretoeat.models.CountriesResponse
import com.example.wheretoeat.models.Restaurant
import com.example.wheretoeat.models.RestaurantByCity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestaurantApiService {

    @GET("restaurants")
    suspend fun getAllRestaurants(
            @Query("city") city: String,
            @Query("page") page: Int
    ): Response<RestaurantByCity>

    @GET("countries")
    suspend fun getCountries(): Response<CountriesResponse>

    @GET("cities")
    suspend fun getCities(): Response<CitiesResponse>

    @GET("restaurants/{id}")
    suspend fun getFavRestById(
            @Path("id") id: Long
    ): Response<Restaurant>
}