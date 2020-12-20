package com.example.wheretoeat.ui.login_register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.example.wheretoeat.MainActivity
import com.example.wheretoeat.R
import com.example.wheretoeat.data.DaoViewModel
import com.example.wheretoeat.models.User

class LoginFragment : Fragment() {
    private val daoViewModel: DaoViewModel by activityViewModels()
    lateinit var userEmail: TextView
    lateinit var userPassword: TextView
    lateinit var allUsers: LiveData<List<User>>
    lateinit var users: List<User>
    lateinit var bundle: Bundle

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        allUsers = daoViewModel.readAllUsers
        allUsers.observe(viewLifecycleOwner, { us ->
            users = us
        })

        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val signupButton = view.findViewById<TextView>(R.id.goto_registration)
        val signinButton = view.findViewById<Button>(R.id.sign_in)

        userEmail = view.findViewById(R.id.user_email_login)
        userPassword = view.findViewById(R.id.password_login)

        signupButton.setOnClickListener {
            findNavController().navigate(R.id.navigation_register)
        }

        /**
         * Listener to the Sign In Button
         */
        signinButton.setOnClickListener {
            val currentUser = validateUser(userEmail.text.toString(), userPassword.text.toString())
            if (currentUser != null) {
                MainActivity.isLoggedIn = true
                findNavController().navigate(R.id.nav_restaurants, bundle)
            } else {
                Toast.makeText(requireContext(), "Wrong email or password! Try again", Toast.LENGTH_SHORT).show()
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

    /**
     * Saving the user's data to use it to display it on the profile fragment
     * and check if the typed email&password are correct
     * @param email String
     * @param password String
     * @return User?
     */
    private fun validateUser(email: String, password: String): User? {
        for (i in users) {
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