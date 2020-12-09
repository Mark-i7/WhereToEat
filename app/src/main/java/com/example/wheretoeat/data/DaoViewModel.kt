package com.example.wheretoeat.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.wheretoeat.models.Favorites
import com.example.wheretoeat.models.Restaurant
import com.example.wheretoeat.models.User
import com.example.wheretoeat.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DaoViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Favorites>>
    private val repository: RestaurantDBRepository


    init {
        val restaurantDao = RestaurantDatabase.getDatabase(application).RestaurantDao()
        repository = RestaurantDBRepository(restaurantDao)
        readAllData = repository.readAllData
    }

    fun addRestaurantDB(favorites: Favorites) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addRestaurant(favorites)
        }
    }

    fun deleteRestaurantDB(favorites: Favorites) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteRestaurant(favorites)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    fun addUserDB(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun deleteUserDB(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(user)
        }
    }


    fun getUserDB(userName: String,password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUser(userName,password)
        }
    }
}
