package com.example.wheretoeat.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretoeat.MainActivity
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

    /**
     * Only 1x called when you need a new View
     * @param parent ViewGroup
     * @param viewType Int
     * @return RestaurantViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.list_item,
                parent, false
        )
        return RestaurantViewHolder(itemView)
    }

    /**
     *  It is called by RecyclerView to display the data at the specified position.
     *  This method is used to update the contents of the itemView
     * @param holder RestaurantViewHolder
     * @param position Int
     */
    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val currentItem = restList[position]

        holder.image.setImageResource(R.drawable.restaurant4)
        holder.r_name.text = currentItem.name
        holder.address.text = currentItem.address
        holder.price.text = currentItem.price.toString() + "$"

        if (isFavoriteForCurr(currentItem)) {
            holder.love_it.setBackgroundResource(R.drawable.heart_after_tap)
        } else {
            holder.love_it.setImageResource(R.drawable.heart_before_tap)
        }

        /**
         * Add restaurant to favorites
         */
//        holder.love_it.setOnClickListener {
//            currentItem.setLiked()
//            holder.love_it.setBackgroundResource(R.drawable.heart_after_tap)
//
//            favorites = Favorites(currentItem.id, Constants.USER_NAME, currentItem.name)
//            daoViewModel.addFavRestDB(favorites)
//            notifyDataSetChanged()
//
//            Snackbar.make(
//                    holder.itemView,
//                    "Restaurant ${currentItem.name} added to favourites",
//                    Snackbar.LENGTH_SHORT
//            ).show()
//        }
        holder.love_it.setOnClickListener {
            if (MainActivity.isLoggedIn) {
                holder.love_it.setBackgroundResource(R.drawable.heart_after_tap)
                favorites = Favorites(currentItem.id, Constants.USER_NAME,currentItem.name)
                daoViewModel.addFavRestDB(favorites)
                Snackbar.make(
                        holder.itemView,
                        "${currentItem.name} added to favourites",
                        Snackbar.LENGTH_SHORT
                ).show()
            }
            else {
                Toast.makeText(context,"You can't add favorites! Please sign in!", Toast.LENGTH_LONG).show()
            }
        }
        /**
         * Remove restaurant from favorites
         */
//        holder.love_it.setOnLongClickListener {
//            holder.love_it.setBackgroundResource(R.drawable.heart_before_tap)
//            daoViewModel.deleteFavRestDB(currentItem.id)
//            Snackbar.make(
//                    holder.itemView,
//                    "Restaurant ${currentItem.name} removed from favourites",
//                    Snackbar.LENGTH_SHORT
//            ).show()
//            notifyDataSetChanged()
//            true
//        }
        holder.love_it.setOnLongClickListener {
            if(isFavoriteForCurr(currentItem)) {
                daoViewModel.deleteFavRestDB(currentItem.id)
                holder.love_it.setBackgroundResource(R.drawable.heart_before_tap)
                Snackbar.make(
                        holder.itemView,
                        "${currentItem.name} removed from favourites",
                        Snackbar.LENGTH_SHORT
                ).show()
                notifyDataSetChanged()
            }
            true
        }

        /**
         * To pass restaurant's details{name,address,city etc] to Details Fragment
         */

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

    /**
     * @param rest a single restaurant object
     * @return boolean `true` if the rest is favorite for the user
     * @return boolean `false` if the rest is NOT favorite for the user
     * */
    private fun isFavoriteForCurr(rest:Restaurant) : Boolean{
        for (i in FavoritesAdapter.favListRest) {
            if (i.name == rest.name && i.id == rest.id) {
                return true
            }
        }
        return false
    }

}
