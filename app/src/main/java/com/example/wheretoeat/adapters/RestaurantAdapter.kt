package com.example.wheretoeat.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretoeat.R
import com.example.wheretoeat.data.DaoViewModel
import com.example.wheretoeat.models.Favorites
import com.example.wheretoeat.models.Restaurant
import com.example.wheretoeat.utils.Constants
import com.google.android.material.snackbar.Snackbar


class RestaurantAdapter(
        private var daoViewModel: DaoViewModel,
        var context: Context,
) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    private var restList: MutableList<Restaurant> = mutableListOf()
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
        val currentItem = restList[position]

        holder.image.setImageResource(R.drawable.restaurant4)
        holder.r_name.text = currentItem.name
        holder.address.text = currentItem.address
        holder.price.text = currentItem.price.toString() + "$"

        if (currentItem.liked) {
            holder.love_it.setBackgroundResource(R.drawable.heart_after_tap)
        } else {
            holder.love_it.setImageResource(R.drawable.heart_before_tap)
        }

        holder.love_it.setOnClickListener {
            currentItem.setLiked()
            holder.love_it.setBackgroundResource(R.drawable.heart_after_tap)

            favorites = Favorites(currentItem.id, Constants.USER_NAME, currentItem.name)
            daoViewModel.addFavRestDB(favorites)
            notifyDataSetChanged()

            Snackbar.make(
                    holder.itemView,
                    "Restaurant ${currentItem.name} added to favourites",
                    Snackbar.LENGTH_SHORT
            ).show()
        }

        holder.love_it.setOnLongClickListener {
            holder.love_it.setBackgroundResource(R.drawable.heart_before_tap)
            daoViewModel.deleteFavRestDB(currentItem.id)
            Snackbar.make(
                    holder.itemView,
                    "Restaurant ${currentItem.name} removed from favourites",
                    Snackbar.LENGTH_SHORT
            ).show()
            notifyDataSetChanged()
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

            holder.itemView.findNavController().navigate(R.id.nav_details, bundle)
        }
    }


    override fun getItemCount() = restList.size

    fun setData(restaurants: MutableList<Restaurant>) {
        this.restList = restaurants
        notifyDataSetChanged()
    }

}
