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
    //add favorite to the database
//    suspend fun addFavorite(favorite: Favorites){
//        restaurantDao.addFavorite(favorite)
//    }
//
//    //delete favorite from database
//    suspend fun deleteFavorite(userId: Int, restaurantId: Int){
//        restaurantDao.deleteFavorite(userId, restaurantId)
//    }
//
//    //check if the restaurant is liked
//    suspend fun isLiked(restaurantId: Int) : Boolean{
//        return restaurantDao.isLiked(restaurantId) != 0
//    }
}