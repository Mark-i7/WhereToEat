package com.example.wheretoeat.data

import androidx.lifecycle.LiveData
import com.example.wheretoeat.models.Favorites
import com.example.wheretoeat.models.Restaurant

class RestaurantDBRepository(private val restaurantDao: RestaurantDao) {

    val readAllData: LiveData<List<Favorites>> = restaurantDao.readAllData()

    suspend fun addRestaurant(favorites: Favorites){
        restaurantDao.addRestaurantDao(favorites)
    }

    suspend fun deleteRestaurant(favorites: Favorites){
        restaurantDao.deleteRestaurant(favorites)
    }

    suspend fun deleteAll(){
        this.restaurantDao.deleteAll()
    }
}