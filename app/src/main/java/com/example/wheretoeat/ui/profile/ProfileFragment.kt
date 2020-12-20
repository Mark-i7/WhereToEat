package com.example.wheretoeat.ui.profile

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.wheretoeat.MainActivity
import com.example.wheretoeat.R
import com.example.wheretoeat.data.DaoViewModel
import com.example.wheretoeat.models.User
import com.example.wheretoeat.models.UserPicture
import com.example.wheretoeat.utils.Constants
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.ByteArrayOutputStream

class ProfileFragment : Fragment() {

    private lateinit var button: FloatingActionButton
    private lateinit var button1: FloatingActionButton

    private val daoViewModel: DaoViewModel by activityViewModels()
    private lateinit var name: TextView
    private lateinit var address: TextView
    private lateinit var phone: TextView
    private lateinit var email: TextView

    lateinit var allUsers: LiveData<List<User>>
    lateinit var users: List<User>

    lateinit var allUserPicture: LiveData<List<UserPicture>>
    var userPictures: List<UserPicture> = listOf()
    private lateinit var profile: ImageView


    @SuppressLint("ResourceType")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        allUsers = daoViewModel.readAllUsers
        val profile_image = root.findViewById<ImageView>(R.id.profilePic)

        allUserPicture = daoViewModel.getAllUserPics
        allUserPicture.observe(viewLifecycleOwner, { us ->
            userPictures = us
            getProfilePicture()
        })
        allUsers.observe(viewLifecycleOwner, { us ->
            users = us
            setProfileData()
        })

        root.apply {
            button = findViewById(R.id.fav_button)
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
            profile = findViewById(R.id.profilePic)
        }
        button1 = view.findViewById(R.id.logout)

        /**
         * Listener to the Logout button
         */
        button1.setOnClickListener {
            logOut()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    /**
     *  The registered detail's  will appear in the Profile section
     */
    private fun setProfileData() {
        for (i in users) {
            if (i.name == Constants.USER_NAME && MainActivity.isLoggedIn) {
                name.text = i.name
                address.text = i.address
                phone.text = i.phone
                email.text = i.email
            }
        }
    }

    /**
     * Load the stored Profile Picture in circleCrop
     */
    private fun getProfilePicture() {
        if (userPictures.isNotEmpty()) {
            for (i in userPictures) {
                if (i.userName == Constants.USER_NAME) {
                    val imgBytes = Base64.decode(i.userPic, Base64.DEFAULT)
                    val decoded = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.size)
                    Glide.with(requireContext())
                            .load(decoded)
                            .circleCrop()
                            .into(profile)
                }
            }
        }
    }

    /**
     * Asking permission to use the gallery/camera
     */
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

    /**
     * Function to choose image
     */
    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE)
    }

    /**
     * Requesting permission from the user
     * @param requestCode Int
     * @param permissions Array<out String>
     * @param grantResults IntArray
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImage()
                }
            }
        }
    }

    /**
     * Saving the choosed picture as ByteArray
     * @param requestCode Int
     * @param resultCode Int
     * @param data Intent?
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE) {
            Glide.with(requireContext())
                    .load(data?.data)
                    .circleCrop()
                    .into(profile)

            val byteArrayOutStream = ByteArrayOutputStream()

            @SuppressWarnings("deprecation")
            val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, data?.data)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutStream)
            val imageBytes: ByteArray = byteArrayOutStream.toByteArray()
            val imgString: String = Base64.encodeToString(imageBytes, Base64.DEFAULT)
            daoViewModel.insertUserPic(UserPicture(Constants.USER_NAME, imgString))
        }
    }

    /**
     *Logout ,
     */
    private fun logOut() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            MainActivity.isLoggedIn = false
            findNavController().navigate(R.id.navigation_login)
            Toast.makeText(
                    requireContext(),
                    "You are logged out!",
                    Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No") { _, _ ->

        }
        builder.setTitle("Do you want to logout?")
        builder.setMessage("Are you sure you want to log out?")
        builder.create().show()
    }

    companion object {
        private const val PICK_IMAGE = 1000
        private const val PERMISSION_CODE = 1001
    }
}