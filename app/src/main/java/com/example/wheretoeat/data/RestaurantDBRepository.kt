package com.example.wheretoeat.data

import androidx.lifecycle.LiveData
import com.example.wheretoeat.models.Favorites
import com.example.wheretoeat.models.Restaurant
import com.example.wheretoeat.models.User

class RestaurantDBRepository(private val restaurantDao: RestaurantDao) {

    val readAllData: LiveData<List<Favorites>> = restaurantDao.readAllData()
    val getAllUsers: LiveData<List<User>> = restaurantDao.selectAllUsers()


    suspend fun addRestaurant(favorites: Favorites){
        restaurantDao.addRestaurantDao(favorites)
    }

    suspend fun deleteRestaurant(favorites: Favorites){
        restaurantDao.deleteRestaurant(favorites)
    }

    suspend fun deleteAll(){
        this.restaurantDao.deleteAll()
    }


    suspend fun insertUser(user:User) {
        restaurantDao.insertUser(user)
    }

    suspend fun deleteAllUsers() {
        restaurantDao.deleteAllUsers()
    }

}