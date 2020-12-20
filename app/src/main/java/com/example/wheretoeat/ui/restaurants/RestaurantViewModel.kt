package com.example.wheretoeat.ui.restaurants

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wheretoeat.models.CitiesResponse
import com.example.wheretoeat.models.CountriesResponse
import com.example.wheretoeat.models.Restaurant
import com.example.wheretoeat.models.RestaurantByCity
import com.example.wheretoeat.repository.RestaurantApiRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class RestaurantViewModel(private val repository: RestaurantApiRepository) : ViewModel() {

    val myResponseAll: MutableLiveData<Response<RestaurantByCity>> = MutableLiveData()
    val myResponseCountry: MutableLiveData<Response<CountriesResponse>> = MutableLiveData()
    val myResponseCity: MutableLiveData<Response<CitiesResponse>> = MutableLiveData()

    fun getAllRestaurant(country: String, page: Int) {
        viewModelScope.launch {
            val response = repository.getAllRestaurants(country, page)
            myResponseAll.value = response
        }
    }

    fun getCountry() {
        viewModelScope.launch {
            val response = repository.getCountries()
            myResponseCountry.value = response
        }
    }

    fun getCity() {
        viewModelScope.launch {
            val response = repository.getCities()
            myResponseCity.value = response
        }
    }

    suspend fun getFavRestById(id: Long): Response<Restaurant> {
        return repository.getFavRestById(id)
    }
}