package com.example.wheretoeat.ui.profile

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.wheretoeat.R
import com.example.wheretoeat.data.DaoViewModel
import com.example.wheretoeat.models.User
import com.example.wheretoeat.utils.Constants
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var button: FloatingActionButton

    private val daoViewModel: DaoViewModel by activityViewModels()
    private lateinit var name:TextView
    private lateinit var address:TextView
    private lateinit var phone :TextView
    private lateinit var email :TextView

    private lateinit var email2 :TextView
    private lateinit var name2 :TextView

    lateinit var allUsers: LiveData<List<User>>
    lateinit var users:List<User>
    var imageUri: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        allUsers = daoViewModel.readAllUsers
        val profile_image = root.findViewById<ImageView>(R.id.profilePic)

        allUsers.observe(viewLifecycleOwner, { us ->
            users = us
            setProfileData()
        })

        root.apply {
            button = findViewById<FloatingActionButton>(R.id.fav_button)
        }

        button.setOnClickListener {
            findNavController().navigate(R.id.nav_favorites)
        }

        profile_image.setOnClickListener {
            //function to change the profile picture
            selectImageFromGallery()
        }

       return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.apply {
            name = findViewById(R.id.edit_name)
            address = findViewById(R.id.edit_address)
            phone = findViewById(R.id.edit_phone_nr)
            email = findViewById(R.id.edit_email)

        }

        super.onViewCreated(view, savedInstanceState)
    }
    fun setProfileData() {
        for (i in users) {
            if(i.name == Constants.USER_NAME) {
                name.text = i.name
                address.text = i.address
                phone.text = i.phone
                email.text = i.email
            }
        }
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

    companion object {
        private val PICK_IMAGE = 1000
        private val PERMISSION_CODE = 1001
    }
}