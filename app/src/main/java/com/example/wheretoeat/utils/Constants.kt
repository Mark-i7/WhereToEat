package com.example.wheretoeat.utils

class Constants {
    companion object {
        //api link
        val BASE_URL = "https://ratpark-api.imok.space/"

        //saving logged-in user
        var USER_NAME: String = ""

        //cities from the api
        var cities: ArrayList<String> = arrayListOf()

        //id of favourites restaurants by current user
        var favRestIds: List<Long> = listOf()
    }
}