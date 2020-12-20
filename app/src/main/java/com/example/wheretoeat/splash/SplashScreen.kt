package com.example.wheretoeat.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.wheretoeat.MainActivity
import com.example.wheretoeat.R
import com.example.wheretoeat.adapters.FavoritesAdapter.Companion.context
import com.example.wheretoeat.repository.RestaurantApiRepository
import com.example.wheretoeat.ui.restaurants.RestaurantViewModel
import com.example.wheretoeat.ui.restaurants.RestaurantViewModelFactory
import com.example.wheretoeat.utils.Constants.Companion.cities

@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {

    private lateinit var viewModel: RestaurantViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        val repository = RestaurantApiRepository()
        val viewModelFactory = RestaurantViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(RestaurantViewModel::class.java)
        viewModel.getCity()
        viewModel.myResponseCity.observe(this, Observer { response ->
            if (response.isSuccessful) {
                cities = response.body()?.cities!!
            } else {
                Toast.makeText(context, "Something is wrong with the API!", Toast.LENGTH_LONG).show()
            }
        })


        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        )


        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 1500)
    }
}