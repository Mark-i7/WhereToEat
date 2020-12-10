package com.example.wheretoeat.ui.login_register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.example.wheretoeat.R
import com.example.wheretoeat.data.DaoViewModel
import com.example.wheretoeat.models.User
import com.example.wheretoeat.viewmodels.SharedViewModel

class LoginFragment : Fragment() {
    private val daoViewModel: DaoViewModel by activityViewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    lateinit var userEmail:TextView
    lateinit var userPassword: TextView
    lateinit var allUsers: LiveData<List<User>>
    lateinit var users:List<User>
    lateinit var bundle: Bundle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        allUsers = daoViewModel.readAllUsers

        allUsers.observe(viewLifecycleOwner, { us ->
            users = us
            Log.d("USER",users.toString())
        })

        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val registerButton = view.findViewById<TextView>(R.id.goto_registration)
        val signinButton = view.findViewById<Button>(R.id.sign_in)
        userEmail = view.findViewById(R.id.user_email_login)
        userPassword = view.findViewById(R.id.password_login)

        registerButton.setOnClickListener{
            findNavController().navigate(R.id.navigation_register)
        }

        signinButton.setOnClickListener {
            val currentUser = validUser(userEmail.text.toString(), userPassword.text.toString())
            if(currentUser!= null) {
                sharedViewModel.isLoggedIn = true
                findNavController().navigate(R.id.nav_restaurants, bundle)
            }
            else {
                Toast.makeText(requireContext(), "Wrong email or password! Try again",Toast.LENGTH_SHORT).show()
            }

        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun validUser(email:String, password:String): User? {
        for(i in users) {
            if (i.email == email && i.password == password) {
                bundle = bundleOf(
                    "name" to i.name,
                    "address" to i.address,
                    "phone" to i.phone,
                    "email" to i.email
                )
                RegisterFragment.setUser(i.name)
                return i
            }
        }
        return null
    }
}