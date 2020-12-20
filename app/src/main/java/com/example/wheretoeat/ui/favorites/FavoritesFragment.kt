package com.example.wheretoeat.ui.favorites

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretoeat.MainActivity
import com.example.wheretoeat.R
import com.example.wheretoeat.adapters.FavoritesAdapter
import com.example.wheretoeat.data.DaoViewModel
import com.example.wheretoeat.utils.Constants
import okhttp3.internal.notify
import okhttp3.internal.notifyAll

class FavoritesFragment : Fragment() {

    private lateinit var favList: RecyclerView
    private val mDaoViewModel: DaoViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_favorites, container, false)

        val adapter = FavoritesAdapter(requireContext(), mDaoViewModel)
        favList = view.findViewById(R.id.recyclerview_favorites)
        favList.adapter = adapter
        favList.layoutManager = LinearLayoutManager(activity)
        favList.setHasFixedSize(true)

        /**
         * Check the user is logged in
         * And  if its logged it will shows the user's favourites restaurants
         */
        if (MainActivity.isLoggedIn) {
            val favRest = mDaoViewModel.getUserFavorites(Constants.USER_NAME)

            favRest.observe(viewLifecycleOwner, { us ->
                Constants.favRestIds = us
                FavoritesAdapter.getFavRestListById(Constants.favRestIds)
                adapter.setData(FavoritesAdapter.favListRest)
            })
        }
        setHasOptionsMenu(true)

        return view
    }

    /**
     * Inflate the delete menu
     * @param menu Menu
     * @param inflater MenuInflater
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_all_menu, menu)
    }

    /**
     * Delete all the liked restaurants
     * @param item MenuItem
     * @return Boolean
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete_all) {

            val builder = AlertDialog.Builder(requireContext())
            builder.setPositiveButton("Yes") { _, _ ->
                mDaoViewModel.deleteAllFavDB()
                Toast.makeText(requireContext(),"Successfully deleted your favorite restaurants!",Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("No") { _, _ ->

            }
            builder.setMessage("Are you sure you want to delete every restaurant from your favourites list?")
            builder.create().show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}