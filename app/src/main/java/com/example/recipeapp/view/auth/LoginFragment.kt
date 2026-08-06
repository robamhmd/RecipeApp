package com.example.recipeapp.view.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentLoginBinding
import com.example.recipeapp.model.AppDatabase
import com.example.recipeapp.model.repository.UserRepository
import com.example.recipeapp.utils.SharedPrefManager
import com.example.recipeapp.view.recipe.RecipeActivity
import com.example.recipeapp.viewmodel.AuthViewModel
import com.example.recipeapp.viewmodel.AuthViewModelFactory
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var authViewModel: AuthViewModel
    private lateinit var sharedPrefManager: SharedPrefManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        sharedPrefManager = SharedPrefManager(requireContext())


        val userDao = AppDatabase.getInstance(requireContext()).userDao()
        val repository = UserRepository(userDao)
        val factory = AuthViewModelFactory(repository)

        authViewModel = ViewModelProvider(
            this,
            factory
        )[AuthViewModel::class.java]


        binding.tvRegister.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.authFragmentContainer, RegisterFragment())
                .addToBackStack(null)
                .commit()
        }


        binding.btnLogin.setOnClickListener {

            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {

                Toast.makeText(
                    requireContext(),
                    "Please fill all fields.",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            lifecycleScope.launch {

                val user = authViewModel.authenticate(username, password)

                if (user != null) {

                    sharedPrefManager.setLoggedIn(true)
                    sharedPrefManager.saveFirstName(user.firstName)

                    Toast.makeText(
                        requireContext(),
                        "Welcome ${user.firstName}!",
                        Toast.LENGTH_SHORT
                    ).show()

                    val intent = Intent(
                        requireContext(),
                        RecipeActivity::class.java
                    )

                    startActivity(intent)
                    requireActivity().finish()

                } else {

                    Toast.makeText(
                        requireContext(),
                        "Invalid username or password.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}