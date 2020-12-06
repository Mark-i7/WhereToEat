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
import com.example.wheretoeat.viewmodels.SharedViewModel
import com.example.wheretoeat.models.Restaurant
import com.example.wheretoeat.utils.Constants
import com.google.android.material.snackbar.Snackbar


class FavoritesAdapter: RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    private var favList = emptyList<Favorites>()

    inner class FavoritesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imageView)
        val r_name: TextView = itemView.findViewById(R.id.r_name)
        //val address: TextView = itemView.findViewById(R.id.address)
        //val price: TextView = itemView.findViewById(R.id.price)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.list_item2,
                parent, false
        )
        return FavoritesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val currentItem = favList[position]

        holder.image.setImageResource(R.drawable.foods)
        holder.r_name.text=currentItem.name
        //holder.address.text=currentItem.address
        //holder.price.text=currentItem.price.toString()

    }


    override fun getItemCount() = favList.size

    fun setData(fav: List<Favorites>) {
        this.favList = fav
        notifyDataSetChanged()
    }
}

