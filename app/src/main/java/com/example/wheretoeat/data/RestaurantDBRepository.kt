package com.example.wheretoeat.data

import androidx.lifecycle.LiveData
import com.example.wheretoeat.models.Restaurant

class RestaurantDBRepository(private val restaurantDao: RestaurantDao) {
    val readAllData: LiveData<List<Restaurant>> = restaurantDao.readAllData()

    suspend fun addRestaurant(restaurant: Restaurant){
        restaurantDao.addRestaurantDao(restaurant)
    }

    suspend fun deleteRestaurant(restaurant: Restaurant){
        restaurantDao.deleteRestaurant(restaurant)
    }

    suspend fun deleteAll(){
        restaurantDao.deleteAll()
    }
}