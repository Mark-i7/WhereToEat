package com.example.wheretoeat.viewmodels

import androidx.lifecycle.ViewModel
import com.example.wheretoeat.models.Restaurant

class SharedViewModel: ViewModel() {

    val favorited: HashMap<Long,ArrayList<Restaurant>> = hashMapOf()

    fun addToFavorites(userId:Long,restaurant:Restaurant){
        var temp = favorited.get(userId)
        if(temp == null){
            temp = arrayListOf(restaurant)
            favorited[userId]=temp
        }else{
            temp.add(restaurant)
        }
    }

    fun getUserFav(userId: Long): List<Restaurant>{
        if(favorited.containsKey(userId)){
            return favorited.getValue(userId)

        }
        return emptyList<Restaurant>()
    }

}