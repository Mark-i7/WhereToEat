package com.example.wheretoeat.ui.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.ViewModelProvider
import com.example.wheretoeat.R
import com.example.wheretoeat.data.DaoViewModel
import com.example.wheretoeat.models.User
import com.example.wheretoeat.utils.Constants
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var button: FloatingActionButton

    private val daoViewModel: DaoViewModel by activityViewModels()
    private lateinit var button1: Button
    private lateinit var name:TextView
    private lateinit var address:TextView
    private lateinit var phone :TextView
    private lateinit var email :TextView
    lateinit var allUsers: LiveData<List<User>>
    lateinit var users:List<User>

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
}