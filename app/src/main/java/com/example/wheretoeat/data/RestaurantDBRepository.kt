package com.example.wheretoeat.data

import androidx.lifecycle.LiveData
import com.example.wheretoeat.models.Favorites
import com.example.wheretoeat.models.User
import com.example.wheretoeat.models.UserPicture

class RestaurantDBRepository(private val restaurantDao: RestaurantDao) {

    val readAllData: LiveData<List<Favorites>> = restaurantDao.readAllData()
    val getAllUsers: LiveData<List<User>> = restaurantDao.selectAllUsers()
    val getAllUserPics: LiveData<List<UserPicture>> = restaurantDao.selectUserPics()


    suspend fun addFavRest(favorites: Favorites) {
        restaurantDao.addFavRest(favorites)
    }

    suspend fun deleteFavRest(restId: Long) {
        restaurantDao.deleteFavRest(restId)
    }

    suspend fun deleteAllFav() {
        this.restaurantDao.deleteAllFav()
    }

    fun getUserFavorites(userName: String): LiveData<List<Long>> {
        return restaurantDao.getUserFavorites(userName)
    }

    suspend fun insertUser(user: User) {
        restaurantDao.insertUser(user)
    }

    suspend fun deleteAllUsers() {
        restaurantDao.deleteAllUsers()
    }

    suspend fun insertUserPic(userPicture: UserPicture) {
        restaurantDao.insertUserPic(userPicture)
    }

}