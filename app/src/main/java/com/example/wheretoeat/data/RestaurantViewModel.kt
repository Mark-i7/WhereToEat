package com.example.wheretoeat.data

import androidx.lifecycle.ViewModel
import com.example.wheretoeat.models.Restaurant
import kotlin.collections.ArrayList

class RestaurantViewModel: ViewModel() {
    private val restaurantList: ArrayList<Restaurant> = arrayListOf()

    fun addRestaurant(restaurant: Restaurant)= restaurantList.add(restaurant)

    fun getRestaurant(id: Int) = restaurantList.getOrNull(id)

    fun getAllRestaurants() = restaurantList

    fun removeRestaurant(pos:Int) = restaurantList.removeAt(pos)


    fun generateDummyData(nr: Int) {
        for (i in 0..nr) {
            val restaurant = Restaurant(i.toLong(),"Restaurant $i","Street $i:","City: $i","State: $i",i.toDouble())
            restaurantList.add(restaurant)
        }
    }
}