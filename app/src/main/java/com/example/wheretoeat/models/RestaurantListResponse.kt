package com.example.wheretoeat.models

class RestaurantListResponse(
    val total_entries: Int,
    val per_page: Int,
    val current_page: Int,
    val restaurants: List<Restaurant>
) {
}