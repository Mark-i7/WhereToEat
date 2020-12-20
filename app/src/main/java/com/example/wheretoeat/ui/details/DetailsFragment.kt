package com.example.wheretoeat.ui.details

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.wheretoeat.R
import kotlinx.android.synthetic.main.details_fragment.*

class DetailsFragment : Fragment() {

    companion object {
        private val PICK_IMAGE = 1000
        private val PERMISSION_CODE = 1001
    }

    private lateinit var myView: View

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        myView = inflater.inflate(R.layout.details_fragment, container, false)

        //Getting the data from the adapter
        val name = requireArguments().get("name").toString()
        val address = requireArguments().get("address").toString()
        val city = requireArguments().get("city").toString()
        val state = requireArguments().get("state").toString()
        val area = requireArguments().get("area").toString()
        val postal_code = requireArguments().get("postal_code").toString()
        val country = requireArguments().get("country").toString()
        val price = requireArguments().get("price").toString()
        val lat = requireArguments().get("lat").toString()
        val lng = requireArguments().get("lng").toString()
        val phone = requireArguments().get("phone").toString()
        val reserve_url = requireArguments().get("reserve_url").toString()
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
        reserveUrl.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(reserve_url))
            startActivity(browserIntent)
        }
        mobileReservation.setOnClickListener {
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
            imageView.setImageURI(data?.data)
        }
    }


}
