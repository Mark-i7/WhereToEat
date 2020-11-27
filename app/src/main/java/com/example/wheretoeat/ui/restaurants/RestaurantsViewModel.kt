package com.example.wheretoeat.ui.restaurants

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RestaurantsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is restaurants Fragment"
    }
    val text: LiveData<String> = _text
}