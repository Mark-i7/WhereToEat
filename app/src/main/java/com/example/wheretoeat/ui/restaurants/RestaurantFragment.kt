package com.example.wheretoeat.ui.restaurants

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretoeat.MainActivity
import com.example.wheretoeat.R
import com.example.wheretoeat.adapters.FavoritesAdapter
import com.example.wheretoeat.adapters.RestaurantAdapter
import com.example.wheretoeat.data.DaoViewModel
import com.example.wheretoeat.models.Restaurant
import com.example.wheretoeat.repository.RestaurantApiRepository
import com.example.wheretoeat.utils.Constants
import com.example.wheretoeat.utils.Constants.Companion.favRestIds
import java.util.*

class RestaurantFragment() : Fragment() {


    private lateinit var daoViewModel: DaoViewModel
    private lateinit var viewModel: RestaurantViewModel
    private var adapter: RestaurantAdapter? = null
    private lateinit var favouritesAdapter: FavoritesAdapter
    private lateinit var restaurantList: RecyclerView


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_restaurants, container, false)

        val search = view.findViewById<TextView>(R.id.search_bar)

        /**
         * Filling up spinner with cities
         */
        val spinner = view.findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val temp_adapter =
                    activity?.let {
                        ArrayAdapter(it, android.R.layout.simple_spinner_item, Constants.cities)
                    }
            spinner.adapter = temp_adapter
        }

        /**
         * Spinner to choose between pages if they are a lot of restaurant
         */
        val pageNum = (1..20).toList()
        val spinnerPage = view.findViewById<Spinner>(R.id.spinnerPage)
        if (spinnerPage != null) {
            val temp_adapter =
                    activity?.let {
                        ArrayAdapter(it, android.R.layout.simple_spinner_item, pageNum)
                    }
            spinnerPage.adapter = temp_adapter
        }
        /**
         * Setting up the adapter for the recyclerView
         */
        daoViewModel = ViewModelProvider(this).get(DaoViewModel::class.java)
        adapter = RestaurantAdapter(daoViewModel, requireContext())
        favouritesAdapter = FavoritesAdapter(requireContext(), daoViewModel)
        restaurantList = view.findViewById(R.id.recycle_view)

        restaurantList.adapter = adapter
        restaurantList.layoutManager = LinearLayoutManager(activity)
        restaurantList.setHasFixedSize(true)

        if (MainActivity.isLoggedIn) {
            val favs = daoViewModel.getUserFavorites(Constants.USER_NAME)

            favs.observe(viewLifecycleOwner, { us ->
                favRestIds = us
                //Log.d("FAVORITIDS", favoritIds.toString())
                FavoritesAdapter.getFavRestListById(favRestIds)
                favouritesAdapter.setData(FavoritesAdapter.favListRest)
            })
        }

        var list: ArrayList<Restaurant> = arrayListOf()

        val repository = RestaurantApiRepository()
        val viewModelFactory = RestaurantViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(RestaurantViewModel::class.java)

        /**
         * Filter by city and page
         */
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                viewModel.getAllRestaurant(
                        spinner.selectedItem.toString(),
                        spinnerPage.selectedItem as Int
                )
                search.text = ""
                spinnerPage?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }

                    override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                    ) {
                        viewModel.getAllRestaurant(
                                spinner.selectedItem.toString(),
                                spinnerPage.selectedItem as Int
                        )
                        search.text = ""
                    }
                }
            }
        }
        /**
         * After filter it will load the restaurants
         */
        viewModel.myResponseAll.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful) {
                if (response.body()?.restaurants!!.isNotEmpty()) {
                    list = (response.body()?.restaurants as ArrayList<Restaurant>?)!!
                    adapter?.setData(list)
                } else {
                    Toast.makeText(context, "Invalid page number!", Toast.LENGTH_SHORT).show()
                    adapter?.setData(mutableListOf())
                }
            }
        })

        /**
         * Search by Restaurant Name
         */
        search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val filteredList = mutableListOf<Restaurant>()
                if (s.toString().isNotEmpty()) {
                    adapter?.setData(list.filter {
                        it.name.toLowerCase(Locale.ROOT)
                                .contains(s.toString().toLowerCase(Locale.ROOT))
                    } as MutableList<Restaurant>)
                } else {
                    adapter?.setData(list)
                }
            }
        })

        return view
    }

}




