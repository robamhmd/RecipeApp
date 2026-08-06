package com.example.recipeapp.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentRegisterBinding
import com.example.recipeapp.model.AppDatabase
import com.example.recipeapp.model.User
import com.example.recipeapp.model.repository.UserRepository
import com.example.recipeapp.viewmodel.AuthViewModel
import com.example.recipeapp.viewmodel.AuthViewModelFactory
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var authViewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val userDao = AppDatabase.getInstance(requireContext()).userDao()
        val repository = UserRepository(userDao)
        val factory = AuthViewModelFactory(repository)

        authViewModel = ViewModelProvider(
            this,
            factory
        )[AuthViewModel::class.java]

        binding.tvLogin.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.authFragmentContainer, LoginFragment())
                .commit()
        }


        binding.btnRegister.setOnClickListener {

            val firstName = binding.etFirstName.text.toString().trim()
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()


            if (firstName.isEmpty() ||
                username.isEmpty() ||
                password.isEmpty() ||
                confirmPassword.isEmpty()
            ) {
                Toast.makeText(
                    requireContext(),
                    "Please fill all fields.",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(
                    requireContext(),
                    "Passwords do not match.",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {

                // Check if username already exists
                if (authViewModel.userExists(username)) {

                    Toast.makeText(
                        requireContext(),
                        "Username already exists.",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {

                    val user = User(
                        firstName = firstName,
                        username = username,
                        passwordHash = password
                    )

                    authViewModel.register(user)

                    Toast.makeText(
                        requireContext(),
                        "Registration successful!",
                        Toast.LENGTH_SHORT
                    ).show()

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.authFragmentContainer, LoginFragment())
                        .commit()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}