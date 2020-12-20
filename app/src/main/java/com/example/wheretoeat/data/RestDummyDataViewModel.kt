package com.example.wheretoeat.data

import androidx.lifecycle.ViewModel
import com.example.wheretoeat.models.Restaurant
import kotlin.collections.ArrayList

class RestDummyDataViewModel: ViewModel() {

    //In case if API doesn't work here are the methods for generate DummyData
    private val restaurantList: ArrayList<Restaurant> = arrayListOf()

    fun addRestaurant(restaurant: Restaurant)= restaurantList.add(restaurant)

    fun getRestaurant(id: Int) = restaurantList.getOrNull(id)

    fun getAllRestaurants() = restaurantList

    fun removeRestaurant(pos:Int) = restaurantList.removeAt(pos)

}