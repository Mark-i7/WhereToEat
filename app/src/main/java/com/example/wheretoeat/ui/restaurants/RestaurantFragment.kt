package com.example.wheretoeat.ui.restaurants

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretoeat.R
import com.example.wheretoeat.adapters.OnItemClickListener
import com.example.wheretoeat.adapters.RestaurantAdapter
import com.example.wheretoeat.data.RestaurantDataViewModel
import com.example.wheretoeat.models.Restaurant
import com.example.wheretoeat.repository.RestaurantApiRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RestaurantFragment() : Fragment() ,CoroutineScope,OnItemClickListener{

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    private  val restaurantDataViewModel: RestaurantDataViewModel by activityViewModels()
    private lateinit var restaurantViewModel:RestaurantViewModel
    private lateinit var restaurantList: RecyclerView
    private lateinit var restaurantAdapter: RestaurantAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val root = inflater.inflate(R.layout.fragment_restaurants, container, false)

        //restaurantAdapter = RestaurantAdapter(restaurantDataViewModel.getAllRestaurants(), this)
        restaurantAdapter= RestaurantAdapter(this)
        restaurantList = root.findViewById(R.id.recyclerView)
        restaurantList.adapter = restaurantAdapter
        restaurantList.layoutManager = LinearLayoutManager(activity)
        //restaurantList.setHasFixedSize(true)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        launch {
            val repository = RestaurantApiRepository()
            val viewModelFactory = RestaurantViewModelFactory(repository)
            restaurantViewModel =ViewModelProvider(requireActivity(), viewModelFactory).get(RestaurantViewModel::class.java)
            lateinit var mylist:List<Restaurant>

            restaurantViewModel.loadRestaurants("Washington")

            restaurantViewModel.response.observe(requireActivity(), { restaurants ->
                mylist=restaurants
                if(restaurants.size == 0) {
                    Toast.makeText(context,"Sorry I can't find restaurants in this city",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context,"I found: "+restaurants.size.toString()+" restaurants",Toast.LENGTH_SHORT).show()
                    restaurantAdapter.setData(restaurants)
                }
                Log.d("apirespons", restaurants.toString())

            })
            super.onViewCreated(view, savedInstanceState)
        }
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(activity, "Item $position clicked", Toast.LENGTH_SHORT).show()
        val bundle = bundleOf("position" to position)
        requireView().findNavController().navigate(R.id.nav_favorites, bundle)
    }

    override fun onItemLongClick(position: Int) {
        val alertDialog: AlertDialog? = activity.let{

            val builder = AlertDialog.Builder(it)
            builder.apply {
                setTitle("Are you sure you want to delete this item?")
                setPositiveButton("Yes",
                    DialogInterface.OnClickListener { dialog, od ->
                        restaurantDataViewModel.removeRestaurant(position)
                        restaurantAdapter.notifyDataSetChanged()
                        Toast.makeText(activity, "Item $position Deleted", Toast.LENGTH_SHORT).show()
                    })
                setNegativeButton("No",
                    DialogInterface.OnClickListener { dialog, id ->
                        Toast.makeText(activity, "Delete cancelled", Toast.LENGTH_SHORT).show()
                    })
            }
            builder.create()
        }
        alertDialog?.show()
    }

}