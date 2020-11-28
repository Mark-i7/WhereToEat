package com.example.wheretoeat.data

import androidx.lifecycle.ViewModel
import com.example.wheretoeat.models.Restaurant
import kotlin.collections.ArrayList

class RestaurantDataViewModel: ViewModel() {

    private val restaurantList: ArrayList<Restaurant> = arrayListOf()

    fun addRestaurant(restaurant: Restaurant)= restaurantList.add(restaurant)

    fun getRestaurant(id: Int) = restaurantList.getOrNull(id)

    fun getAllRestaurants() = restaurantList

    fun removeRestaurant(pos:Int) = restaurantList.removeAt(pos)



    fun generateDummyData(nr: Int) {
        for(i in 0..nr) {
            val restaurant = Restaurant((i*1312421).toLong(),
                "Restaurant $i",
                "street $i",
                i.toString(),
                "US",
                "t",
                "blabl0",
                "America",
                "411312",
                7623478-i*95*10234.00,
                (i*5-65).toDouble(),
                i,
                "facebookdf.com",
                "asd.com",
                "https://png.pngtree.com/png-clipart/20190611/original/pngtree-cartoon-restaurant-png-image_2979475.jpg")
            restaurantList.add(restaurant)
        }
    }
}