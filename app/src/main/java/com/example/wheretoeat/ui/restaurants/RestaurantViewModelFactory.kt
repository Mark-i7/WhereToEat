package com.example.wheretoeat.ui.restaurants

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wheretoeat.repository.RestaurantApiRepository


class RestaurantViewModelFactory(private val repository: RestaurantApiRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RestaurantViewModel(repository) as T
    }


}