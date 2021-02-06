package com.example.wheretoeat.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretoeat.R
import com.example.wheretoeat.data.DaoViewModel
import com.example.wheretoeat.models.Restaurant
import com.example.wheretoeat.repository.RestaurantApiRepository
import com.example.wheretoeat.ui.restaurants.RestaurantViewModel
import com.example.wheretoeat.ui.restaurants.RestaurantViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FavoritesAdapter(
        private val context: Context,
        val daoViewModel: DaoViewModel
) : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    private var favList = emptyList<Restaurant>()

    inner class FavoritesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imageView)
        val r_name: TextView = itemView.findViewById(R.id.r_name)
    }

    /**
     * Only 1x called when you need a new View
     * @param parent ViewGroup
     * @param viewType Int
     * @return FavoritesViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_fav,
                parent, false
        )
        Companion.context = context
        return FavoritesViewHolder(itemView)
    }

    /**
     *  It is called by RecyclerView to display the data at the specified position.
     *  This method is used to update the contents of the itemView
     * @param holder FavoritesViewHolder
     * @param position Int
     */
    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val currentItem = favList[position]
        holder.image.setImageResource(R.drawable.restaurant4)
        holder.r_name.text = currentItem.name

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

    override fun getItemCount() = favList.size

    fun setData(fav: List<Restaurant>) {
        this.favList = fav
        notifyDataSetChanged()
    }


    companion object : CoroutineScope {
        var favListRest = emptyList<Restaurant>()
        lateinit var context: Context
        lateinit var restViewModel: RestaurantViewModel

        override val coroutineContext: CoroutineContext
            get() = Dispatchers.Main + Job()

        /**
         * This method gave the User's Favorite's list
         * Every users have their own Favorite's list
         * @param favRestId List<Long>
         */
        fun getFavRestListById(favRestId: List<Long>) {
            val favRestMutableList: MutableList<Restaurant> = mutableListOf()
            launch {
                val repository = RestaurantApiRepository()
                val viewModelFactory = RestaurantViewModelFactory(repository)
                restViewModel = ViewModelProvider(ViewModelStore(), viewModelFactory).get(RestaurantViewModel::class.java)
                if (favRestId.isNotEmpty()) {
                    for (i in favRestId) {
                        val myResponseRestById = restViewModel.getFavRestById(i)
                        if (myResponseRestById.isSuccessful && myResponseRestById.body() != null) {
                            val responeBody = myResponseRestById.body()
                            if (responeBody != null) {
                                favRestMutableList.add(responeBody)
                            }
                        }
                    }
                }
                favListRest = favRestMutableList.toList()
            }
        }
    }

}