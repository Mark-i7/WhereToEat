package com.example.wheretoeat.repository

import com.example.wheretoeat.api.RetrofitInstance
import com.example.wheretoeat.models.CitiesResponse
import com.example.wheretoeat.models.CountriesResponse
import com.example.wheretoeat.models.Restaurant
import com.example.wheretoeat.models.RestaurantByCity
import retrofit2.Response

class RestaurantApiRepository {

    suspend fun getAllRestaurants(country: String, page: Int): Response<RestaurantByCity> {
        return RetrofitInstance.api.getAllRestaurants(country, page)
    }

    suspend fun getCountries(): Response<CountriesResponse> {
        return RetrofitInstance.api.getCountries()
    }

    suspend fun getCities(): Response<CitiesResponse> {
        return RetrofitInstance.api.getCities()
    }

    suspend fun getFavRestById(id: Long): Response<Restaurant> {
        return RetrofitInstance.api.getFavRestById(id)
    }
}