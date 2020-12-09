package com.example.wheretoeat.data

import androidx.lifecycle.LiveData
import com.example.wheretoeat.models.Favorites
import com.example.wheretoeat.models.Restaurant
import com.example.wheretoeat.models.User

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

    suspend fun addUser(user: User){
        restaurantDao.addUser(user)
    }

    suspend fun deleteUser(user: User){
        restaurantDao.deleteUser(user)
    }

    suspend fun getUser(userName:String,password:String){
        restaurantDao.getUser(userName,password)
    }

}