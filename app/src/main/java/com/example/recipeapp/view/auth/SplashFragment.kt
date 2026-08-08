package com.example.recipeapp.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.recipeapp.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.example.recipeapp.utils.SharedPrefManager
import com.example.recipeapp.view.auth.recipe.RecipeActivity

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({

            val sharedPrefManager = SharedPrefManager(requireContext())

            if (sharedPrefManager.isLoggedIn()) {

                val intent = Intent(requireContext(), RecipeActivity::class.java)
                startActivity(intent)
                requireActivity().finish()

            } else {

                parentFragmentManager.beginTransaction()
                    .replace(R.id.authFragmentContainer, LoginFragment())
                    .commit()

            }

        }, 5000)
    }
}
