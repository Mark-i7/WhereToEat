package com.example.wheretoeat.ui.details

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.wheretoeat.R
import com.example.wheretoeat.models.Restaurant
import com.example.wheretoeat.viewmodels.SharedViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_profile.*

class DetailsFragment : Fragment() {

    companion object {
        fun newInstance() = DetailsFragment()
        private val PICK_IMAGE = 1000
        private val PERMISSION_CODE = 1001
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

        val profile_image = myView.findViewById<ImageView>(R.id.imageView)

        myView.findViewById<TextView>(R.id.r_name).text = name
        myView.findViewById<TextView>(R.id.address).text = address
        myView.findViewById<TextView>(R.id.city).text = city
        myView.findViewById<TextView>(R.id.state).text = state
        myView.findViewById<TextView>(R.id.area).text = area
        myView.findViewById<TextView>(R.id.postalcode).text = postal_code
        myView.findViewById<TextView>(R.id.country).text = country
        myView.findViewById<TextView>(R.id.price).text = price
//        myView.findViewById<ImageView>(R.id.reserve_url).text = reserve_url
//        myView.findViewById<ImageView>(R.id.mobile_reserve).text = mobile_reserve_url

        val mapButton = myView.findViewById<ImageButton>(R.id.view_location_map)
        val callButton = myView.findViewById<ImageButton>(R.id.call_the_place)
        val reserveUrl = myView.findViewById<ImageView>(R.id.reserve_url)
        val mobileReservation = myView.findViewById<ImageView>(R.id.mobile_reserve)

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

        profile_image.setOnClickListener {
            //function to change the profile picture
            selectImageFromGallery()
        }

        return myView
    }
    private fun selectImageFromGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) ==
                PackageManager.PERMISSION_DENIED) {
                val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permissions, PERMISSION_CODE)
            } else {
                pickImage()
            }
        } else {
            //system OS is > current
            pickImage()
        }
    }

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE)
    }

    //requesting permission from the user
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImage()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE) {
            Glide.with(requireContext())
                .load(data?.data)
                .into(profilePic)
//            //saving profile pic uri in a variable, then saving it, if the user logs out
//            imageUri = data?.data.toString()
//            Toast.makeText(context, "To save the profile picture you have to logout first!", Toast.LENGTH_LONG).show()
            profilePic.setImageURI(data?.data)
        }
    }


    }
