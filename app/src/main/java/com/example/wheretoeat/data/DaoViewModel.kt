package com.example.wheretoeat.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.wheretoeat.models.Favorites
import com.example.wheretoeat.models.Restaurant
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
}
//    fun like (restaurant : Restaurant){
//        viewModelScope.launch {
//
//            val entityFavorite = Favorites(
//                    0,
//                    Constants.USER_ID,
//                    restaurant.id,
//                    restaurant.name,
//                    restaurant.address,
//                    restaurant.city,
//                    restaurant.state,
//                    restaurant.area,
//                    restaurant.postal_code,
//                    restaurant.country,
//                    restaurant.phone,
//                    restaurant.lat,
//                    restaurant.lng,
//                    restaurant.price,
//                    restaurant.mobile_reserve_url,
//                    restaurant.mobile_reserve_url,
//                    restaurant.image_url
//            )
//            if (repository.isLiked(entityFavorite.restaurantId.toInt())){
//                repository.deleteFavorite(Constants.USER_ID.toInt(), entityFavorite.restaurantId.toInt())
//                if (favorites.value != null) {
//                    if (favorites.value!!.size == 1){
//                        favorites.value = mutableListOf()
//                    }
//                    else{
//                        favorites.value!!.remove(entityFavorite)
//                    }
//
//                }
//                //favorites.value?.remove(entityFavorite)
//            }
//            else{
//                repository.addFavorite(entityFavorite)
//                favorites.value?.add(entityFavorite)
//            }
//
//
//        }
//    }
//
//    fun deleteFavorite(entityFavorite: Favorites){
//        viewModelScope.launch {
//            repository.deleteFavorite(Constants.USER_ID.toInt(), entityFavorite.restaurantId.toInt())
//            if (favorites.value != null)  favorites.value!!.remove(entityFavorite)
//        }
//    }
//}
