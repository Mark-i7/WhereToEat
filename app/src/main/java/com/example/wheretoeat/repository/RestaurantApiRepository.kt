package com.example.wheretoeat.repository

import com.example.wheretoeat.api.RetrofitInstance
import com.example.wheretoeat.models.RestaurantByCity

class RestaurantApiRepository {
    suspend fun getRestaurantsByCity(city: String): RestaurantByCity {
        return RetrofitInstance.api.getRestaurantsByCity(city)
    }
}