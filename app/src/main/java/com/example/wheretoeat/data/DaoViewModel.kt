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
    val readAllUsers: LiveData<List<User>>
        get() {
            return repository.getAllUsers
        }


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
            repository.insertUser(user)
        }
    }

    fun deleteAllUserDB() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllUsers()
        }
    }
}
