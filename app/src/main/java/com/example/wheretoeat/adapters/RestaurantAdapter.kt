package com.example.wheretoeat.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretoeat.R
import com.example.wheretoeat.models.Restaurant
import com.example.wheretoeat.ui.restaurants.RestaurantFragment


class RestaurantAdapter(
        private var restaurantList: List<Restaurant>,
        private val listener: RestaurantFragment
): RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>(){


    inner class RestaurantViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {
        val image: ImageView = itemView.findViewById(R.id.imageView)
        val r_name: TextView = itemView.findViewById(R.id.r_name)
        val address: TextView = itemView.findViewById(R.id.address)
        val price: TextView = itemView.findViewById(R.id.price)


        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            if(position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }

        override fun onLongClick(v: View?): Boolean {
            val position: Int = adapterPosition
            if(position != RecyclerView.NO_POSITION) {
                listener.onItemLongClick(position)
            }
            return true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.list_item,
                parent, false
        )
        return RestaurantViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val currentItem = restaurantList[position]
        holder.image.setImageResource(R.drawable.ic_menu_camera)
        holder.r_name.text=currentItem.name
        holder.address.text=currentItem.address
        holder.price.text=currentItem.price.toString()
    }

    override fun getItemCount() = restaurantList.size

    fun setData(newList: List<Restaurant>){
        restaurantList=newList
        notifyDataSetChanged()
    }

}
interface OnItemClickListener {
    fun onItemClick(position: Int)
    fun onItemLongClick(position: Int)
}