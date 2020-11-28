package com.example.wheretoeat.ui.restaurants

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wheretoeat.models.Restaurant
import com.example.wheretoeat.models.RestaurantByCity
import com.example.wheretoeat.repository.RestaurantApiRepository
import retrofit2.Response

class RestaurantViewModel(private val repository: RestaurantApiRepository) : ViewModel(){

    val response: MutableLiveData<List<Restaurant>> = MutableLiveData()
    val myCustomCity: MutableLiveData<Response<List<Restaurant>>> = MutableLiveData()

        suspend fun getRestaurantsByCity(city: String) {
            val res = repository.getRestaurantsByCity(city)
            response.value = restaurantByCityConverter(res)
        }


    private fun restaurantByCityConverter(restaurantsByCity: RestaurantByCity): List<Restaurant> {
        val list = mutableListOf<Restaurant>()

        for(i in restaurantsByCity.restaurants){
            val restaurant = Restaurant (
                i.id,
                i.name,
                i.address,
                i.city,
                i.state,
                i.area,
                i.postal_code,
                i.country,
                i.phone,
                i.lat,
                i.lng,
                i.price,
                i.reserve_url,
                i.mobile_reserve_url,
                i.image_url
            )
            list.add(restaurant)
        }
        return list
    }

    suspend fun loadRestaurants(city: String) {
        getRestaurantsByCity(city)
    }

}