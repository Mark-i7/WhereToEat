package com.example.wheretoeat.ui.login_register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.example.wheretoeat.MainActivity
import com.example.wheretoeat.R
import com.example.wheretoeat.data.DaoViewModel
import com.example.wheretoeat.models.User
import com.example.wheretoeat.utils.Constants

class RegisterFragment : Fragment() {
    private val daoViewModel: DaoViewModel by activityViewModels()
    private lateinit var user: User
    lateinit var users: List<User>
    lateinit var allUsers: LiveData<List<User>>


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        allUsers = daoViewModel.readAllUsers
        allUsers.observe(viewLifecycleOwner, { us ->
            users = us
        })

        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val userName = view.findViewById<EditText>(R.id.user_name)
        val userAddress = view.findViewById<EditText>(R.id.user_adress)
        val userPhone = view.findViewById<EditText>(R.id.user_phone)
        val userPassword = view.findViewById<EditText>(R.id.password)
        val userEmail = view.findViewById<EditText>(R.id.user_email)


        val signupButton = view.findViewById<Button>(R.id.finish_registration)

        signupButton.setOnClickListener {
            user = User(
                    userName.text.toString(),
                    userAddress.text.toString(),
                    userPhone.text.toString(),
                    userEmail.text.toString(),
                    userPassword.text.toString()
            )

            if (isRegistered(user)) {
                Toast.makeText(context, "You are a registered user ! Did you forgot your password?", Toast.LENGTH_LONG).show()
            } else {
                MainActivity.isLoggedIn = true
                Log.d("Successful sign up!", user.name)
                daoViewModel.addUserDB(user)
                setUser(user.name)
                findNavController().navigate(R.id.nav_restaurants)
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun setUser(name: String) {
            Constants.USER_NAME = name
        }
    }

    private fun isRegistered(user: User): Boolean {
        for (u in users) {
            if ((u.email == user.email) && (u.password == user.password)) {
                return true
            }
        }
        return false
    }
}