package com.example.wheretoeat.models

data class RestaurantByCity(val count:Int, val per_page:Int, val current_page:Int, val restaurants:List<Restaurant> ){

}