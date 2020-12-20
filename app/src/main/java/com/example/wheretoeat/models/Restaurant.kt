package com.example.wheretoeat.models

data class Restaurant(
        var id: Long,
        var name: String,
        var address: String,
        val city: String,
        val state: String,
        val area: String,
        val postal_code: Long,
        val country: String,
        val phone: String,
        val lat: Double,
        val lng: Double,
        var price: Long,
        val reserve_url: String,
        val mobile_reserve_url: String,
        val image_url: String
) {
    var liked: Boolean = false
    fun setLiked() = !liked
}
