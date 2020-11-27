package com.example.wheretoeat.models

class Restaurant(var id:Long, var name:String, var address:String, val city:String, val state:String,val price:Double) {
    override fun toString(): String {
        return "Restaurant(id=$id, name='$name', address='$address', city='$city', state='$state', price='$price')"
    }
}