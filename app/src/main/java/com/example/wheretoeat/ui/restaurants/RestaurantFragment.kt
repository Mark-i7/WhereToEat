package com.example.wheretoeat.ui.restaurants

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wheretoeat.R
import androidx.lifecycle.Observer
import com.example.wheretoeat.adapters.RestaurantAdapter
import com.example.wheretoeat.data.DaoViewModel
import com.example.wheretoeat.models.Restaurant
import com.example.wheretoeat.repository.RestaurantApiRepository
import com.example.wheretoeat.utils.Constants
import kotlinx.android.synthetic.main.fragment_restaurants.view.*
import java.util.*
import kotlin.collections.ArrayList

class RestaurantFragment() : Fragment() {
//
//    override val coroutineContext: CoroutineContext
//        get() = Dispatchers.Main + Job()
//regi jo
//    private  val restaurantDataViewModel: RestaurantDataViewModel by activityViewModels()
//    private lateinit var restaurantViewModel:RestaurantViewModel
//    private lateinit var restaurantList: RecyclerView
//    private lateinit var restaurantAdapter: RestaurantAdapter
//    private val daoViewModel: DaoViewModel by activityViewModels()


    private lateinit var daoViewModel: DaoViewModel
    private lateinit var viewModel: RestaurantViewModel
    private var adapter: RestaurantAdapter? = null
    private var mySearch: CharSequence = ""

    //    private  val sharedViewModel : SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_restaurants, container, false)

        val search = view.findViewById<TextView>(R.id.search_bar)

        val spinner = view.findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val temp_adapter =
                activity?.let {
                    ArrayAdapter(it, android.R.layout.simple_spinner_item, Constants.cities)
                }
            spinner.adapter = temp_adapter
        }

        val pageNum = (1..20).toList()
        val spinnerPage = view.findViewById<Spinner>(R.id.spinnerPage)
        if (spinnerPage != null) {
            val temp_adapter =
                activity?.let {
                    ArrayAdapter(it, android.R.layout.simple_spinner_item, pageNum)
                }
            spinnerPage.adapter = temp_adapter
        }

        daoViewModel = ViewModelProvider(this).get(DaoViewModel::class.java)
        adapter = context?.let { RestaurantAdapter(daoViewModel, it) }

        view.recycle_view.adapter = adapter
        view.recycle_view.layoutManager = LinearLayoutManager(requireContext())
        view.recycle_view.setHasFixedSize(true)

        var list: ArrayList<Restaurant> = arrayListOf()

        val repository = RestaurantApiRepository()
        val viewModelFactory = RestaurantViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(RestaurantViewModel::class.java)

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.getAllRestaurant(
                    spinner.selectedItem.toString(),
                    spinnerPage.selectedItem as Int
                )
                search.text = ""
                spinnerPage?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        viewModel.getAllRestaurant(
                            spinner.selectedItem.toString(),
                            spinnerPage.selectedItem as Int
                        )
                        search.text = ""
                    }
                }
            }
        }

        viewModel.myResponseAll.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful) {
                if (response.body()?.restaurants!!.isNotEmpty()) {
                    list = (response.body()?.restaurants as ArrayList<Restaurant>?)!!
                    adapter?.setData(list)
                } else {
                    Toast.makeText(context, "Invalid page number!", Toast.LENGTH_SHORT).show()
                    adapter?.setData(mutableListOf())
                }
                Log.d("List size: ", list.size.toString())

            }
        })

        search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val filteredList = mutableListOf<Restaurant>()
                if (s.toString().isNotEmpty()) {
                    adapter?.setData(list.filter {
                        it.name.toLowerCase(Locale.ROOT)
                            .contains(s.toString().toLowerCase(Locale.ROOT))
                    } as MutableList<Restaurant>)
                } else {
                    adapter?.setData(list)
                }
            }
        })

        return view
    }

}




