package com.example.wheretoeat.ui.details

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.example.wheretoeat.R
import com.example.wheretoeat.models.Restaurant
import com.example.wheretoeat.viewmodels.SharedViewModel

class DetailsFragment : Fragment() {

    companion object {
        fun newInstance() = DetailsFragment()
    }

    private lateinit var detailsViewModel: DetailsViewModel
    private lateinit var restaurant: Restaurant
    private var model: SharedViewModel?=null
    private val TAG = "DetailsFragment"
    private lateinit var myView: View

//    private val viewModel: SharedViewModel by lazy {
//        ViewModelProvider(this).get(SharedViewModel::class.java)
//    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        detailsViewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myView=inflater.inflate(R.layout.details_fragment, container, false)

        //Getting the data from the adapter
        val name = requireArguments().get("name").toString()
        val address = requireArguments().get("address").toString()
        val city = requireArguments().get("city").toString()
        val state = requireArguments().get("state").toString()
        val area = requireArguments().get("area").toString()
        val postal_code = requireArguments().get("postal_code").toString()
        val country  = requireArguments().get("country").toString()
        val price = requireArguments().get("price").toString()
        val lat = requireArguments().get("lat").toString()
        val lng = requireArguments().get("lng").toString()
        val phone = requireArguments().get("phone").toString()
        var reserve_url = requireArguments().get("reserve_url").toString()
        val mobile_reserve_url = requireArguments().get("mobile_reserve_url").toString()

        myView.findViewById<TextView>(R.id.r_name).text = name
        myView.findViewById<TextView>(R.id.address).text = address
        myView.findViewById<TextView>(R.id.city).text = city
        myView.findViewById<TextView>(R.id.state).text = state
        myView.findViewById<TextView>(R.id.area).text = area
        myView.findViewById<TextView>(R.id.postalcode).text = postal_code
        myView.findViewById<TextView>(R.id.country).text = country
        myView.findViewById<TextView>(R.id.price).text = price
        myView.findViewById<TextView>(R.id.reserve_url).text = reserve_url
        myView.findViewById<TextView>(R.id.mobile_reserve).text = mobile_reserve_url

        val mapButton = myView.findViewById<ImageButton>(R.id.view_location_map)
        val callButton = myView.findViewById<ImageButton>(R.id.call_the_place)
        val reserveUrl = myView.findViewById<TextView>(R.id.reserve_url)
        val mobileReservation = myView.findViewById<TextView>(R.id.mobile_reserve)

        mapButton.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:$lat,$lng")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        callButton.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_DIAL
            intent.data = Uri.parse("tel:$phone")
            startActivity(intent)
        }
        reserveUrl.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(reserve_url))
            startActivity(browserIntent)
        }
        mobileReservation.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(mobile_reserve_url))
            startActivity(browserIntent)
        }

        return myView
    }

    }
