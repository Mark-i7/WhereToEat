package com.example.wheretoeat.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.wheretoeat.models.Favorites
import com.example.wheretoeat.models.User
import com.example.wheretoeat.models.UserPicture
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DaoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RestaurantDBRepository
    val readAllData: LiveData<List<Favorites>>
    val readAllUsers: LiveData<List<User>>
    val getAllUserPics: LiveData<List<UserPicture>>


    init {
        val restaurantDao = RestaurantDatabase.getDatabase(application).RestaurantDao()
        repository = RestaurantDBRepository(restaurantDao)
        readAllData = repository.readAllData
        readAllUsers = repository.getAllUsers
        getAllUserPics = repository.getAllUserPics
    }

    fun addFavRestDB(favorites: Favorites) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFavRest(favorites)
        }
    }

    fun deleteFavRestDB(restId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavRest(restId)
        }
    }

    fun deleteAllFavDB() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllFav()
        }
    }

    fun addUserDB(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertUser(user)
        }
    }

    fun deleteAllUserDB() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllUsers()
        }
    }

    fun getUserFavorites(userName: String): LiveData<List<Long>> {
        return repository.getUserFavorites(userName)
    }

    fun insertUserPic(userPicture: UserPicture) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertUserPic(userPicture)
        }
    }

}