package com.example.wheretoeat.utils

class Constants {
    companion object {
        val BASE_URL = "http://opentable.herokuapp.com/api/"
        val USER_ID = 12345.toLong()
        var USER_NAME:String = ""
        val BASE_IMAGE_URL = "https://www.opentable.com/img/restimages/"
        lateinit var states: ArrayList<String>
//        lateinit var cities: ArrayList<String>
        var cities: ArrayList<String> = arrayListOf()


    }
}