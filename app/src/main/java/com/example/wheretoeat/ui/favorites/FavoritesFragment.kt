package com.example.wheretoeat.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretoeat.R
import com.example.wheretoeat.adapters.FavoritesAdapter
import com.example.wheretoeat.adapters.RestaurantAdapter
import com.example.wheretoeat.data.DaoViewModel
import com.example.wheretoeat.ui.restaurants.RestaurantViewModel
import com.example.wheretoeat.viewmodels.SharedViewModel
import kotlinx.android.synthetic.main.list_item.view.*

class FavoritesFragment : Fragment() {

    private lateinit var favoritesViewModel: FavoritesViewModel
    private lateinit var restViewModel: RestaurantViewModel
    //private lateinit var favoritesList:SharedViewModel
    private lateinit var favList: RecyclerView
    private lateinit var mDaoViewModel: DaoViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
//        favoritesViewModel =
//                ViewModelProvider(this).get(FavoritesViewModel::class.java)

        val view = inflater.inflate(R.layout.fragment_favorites, container, false)
//        view.love_it.setOnClickListener {
//            insertDataToDatabase()
//        }

        //favAdapter= RestaurantAdapter(this,requireContext(),sharedViewModel,daoViewModel)
        val adapter=FavoritesAdapter()
        favList = view.findViewById(R.id.recyclerView2)
        favList.adapter = adapter
        favList.layoutManager = LinearLayoutManager(activity)
        mDaoViewModel=ViewModelProvider(this).get(DaoViewModel::class.java)
        mDaoViewModel.readAllData.observe(viewLifecycleOwner, Observer { favorites ->
            adapter.setData(favorites)
        })
        return view
    }
//
//    private fun insertDataToDatabase() {
//        val id=
//    }
}