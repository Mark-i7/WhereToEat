package com.example.wheretoeat.adapters

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretoeat.R
import com.example.wheretoeat.data.DaoViewModel
import com.example.wheretoeat.models.Favorites
import com.example.wheretoeat.viewmodels.SharedViewModel
import com.example.wheretoeat.models.Restaurant
import com.example.wheretoeat.ui.details.DetailsFragment
import com.example.wheretoeat.utils.Constants
import com.google.android.material.snackbar.Snackbar
import java.util.*


class RestaurantAdapter(private var daoViewModel: DaoViewModel,
                        var context:Context,
                        ): RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    private var exampleList: MutableList<Restaurant> = mutableListOf()

//    private var context = mContext
    private lateinit var favorites: Favorites


    inner class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imageView)
        val r_name: TextView = itemView.findViewById(R.id.r_name)
        val address: TextView = itemView.findViewById(R.id.address)
        val price: TextView = itemView.findViewById(R.id.price)
        val love_it: ImageView = itemView.findViewById(R.id.love_it)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item,
            parent, false
        )
        return RestaurantViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val currentItem = exampleList[position]
//        holder.image.setImageResource(R.drawable.foods)
//        holder.image.setImageResource(R.drawable.restaurant1)


        if(currentItem.id.toInt() % 2 == 0) {
            holder.image.setImageResource(R.drawable.restaurant1)
        }else if(currentItem.id.toInt() % 3 ==0){
            holder.image.setImageResource(R.drawable.restaurant2)
        }else if(currentItem.id.toInt() % 7 ==0){
            holder.image.setImageResource(R.drawable.restaurant4)
        }else{
            holder.image.setImageResource(R.drawable.restaurant3)
        }


        holder.r_name.text = currentItem.name
        holder.address.text = currentItem.address
        holder.price.text = currentItem.price.toString()+"$"

//        holder.love_it.setImageResource(R.drawable.heart_before_tap)
        if(currentItem.liked){
            holder.love_it.setBackgroundResource(R.drawable.like)
        }else{
            holder.love_it.setImageResource(R.drawable.heart_before_tap)
        }


        holder.love_it.setOnClickListener {
//            viewModel.addToFavorites(Constants.USER_ID, currentItem)
            currentItem.setLiked()

            holder.love_it.setBackgroundResource(R.drawable.like)

            favorites = Favorites(currentItem.id, Constants.USER_ID, currentItem.name)
            daoViewModel.addRestaurantDB(favorites)
            notifyDataSetChanged()

            Snackbar.make(
                holder.itemView,
                "Item ${currentItem.id} clicked",
                Snackbar.LENGTH_SHORT
            ).show()
        }
        holder.love_it.setOnLongClickListener {
            holder.love_it.setBackgroundResource(R.drawable.heart_before_tap)

//            favorites= Favorites(currentItem.id,Constants.USER_ID,currentItem.name)
//            daoViewModel.deleteRestaurantDB(favorites)

            daoViewModel.deleteAll()
            notifyDataSetChanged()
            Snackbar.make(
                holder.itemView,
                "Item ${currentItem.id} removed from favourites",
                Snackbar.LENGTH_SHORT
            ).show()
            true
        }
        holder.itemView.setOnClickListener {
            val bundle = bundleOf(
                "name" to currentItem.name,
                "address" to currentItem.address,
                "city" to currentItem.city,
                "state" to currentItem.state,
                "area" to currentItem.area,
                "postal_code" to currentItem.postal_code,
                "country" to currentItem.country,
                "price" to currentItem.price,
                "lat" to currentItem.lat,
                "lng" to currentItem.lng,
                "phone" to currentItem.phone,
                "reserve_url" to currentItem.reserve_url,
                "mobile_reserve_url" to currentItem.mobile_reserve_url
            )

            Toast.makeText(context, "Item $position clicked", Toast.LENGTH_SHORT).show()

            holder.itemView.findNavController().navigate(R.id.nav_details, bundle)
        }
    }


    override fun getItemCount() = exampleList.size

    fun setData(restaurants: MutableList<Restaurant>) {
        this.exampleList = restaurants
        notifyDataSetChanged()
    }

}
