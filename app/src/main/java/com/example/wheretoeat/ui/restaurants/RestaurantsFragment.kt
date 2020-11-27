package com.example.wheretoeat.ui.restaurants

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretoeat.R
import com.example.wheretoeat.adapters.RestaurantAdapter
import com.example.wheretoeat.data.RestaurantViewModel

class RestaurantsFragment : Fragment() {

    private val restaurantViewModel: RestaurantViewModel by activityViewModels()
    private lateinit var restaurantList: RecyclerView
    private lateinit var restaurantAdapter: RestaurantAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {


        val root = inflater.inflate(R.layout.fragment_restaurants, container, false)

        restaurantAdapter = RestaurantAdapter(restaurantViewModel.getAllRestaurants(), this)
        restaurantList = root.findViewById(R.id.recyclerView)
        restaurantList.adapter = restaurantAdapter
        restaurantList.layoutManager = LinearLayoutManager(activity)
        restaurantList.setHasFixedSize(true)

        return root
    }
    fun onItemClick(position: Int) {
        Toast.makeText(activity, "Item $position clicked", Toast.LENGTH_SHORT).show()
        val bundle = bundleOf("position" to position)
        requireView().findNavController().navigate(R.id.nav_favorites, bundle)
    }

    fun onItemLongClick(position: Int) {
        val alertDialog: AlertDialog? = activity.let{

            val builder = AlertDialog.Builder(it)
            builder.apply {
                setTitle("Are you sure you want to delete this item?")
                setPositiveButton("Yes",
                    DialogInterface.OnClickListener { dialog, od ->
                        restaurantViewModel.removeRestaurant(position)
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