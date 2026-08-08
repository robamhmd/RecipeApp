package com.example.recipeapp.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.model.AppDatabase
import com.example.recipeapp.model.Favorite
import com.example.recipeapp.utils.SharedPrefManager
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment() {

    private lateinit var favoritesRecyclerView: RecyclerView
    private lateinit var favoriteAdapter: FavoriteAdapter

    private lateinit var sharedPrefManager: SharedPrefManager
    private lateinit var currentUser: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_favorite,
            container,
            false
        )

        favoritesRecyclerView = view.findViewById(R.id.favoritesRecyclerView)

        sharedPrefManager = SharedPrefManager(requireContext())
        // حل مؤقت: بنستخدم الاسم الأول كمعرّف للمستخدم لحد ما نضيف username حقيقي
        currentUser = sharedPrefManager.getFirstName()

        favoriteAdapter = FavoriteAdapter(
            emptyList()
        ) { favorite ->
            removeFavorite(favorite)
        }

        favoritesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        favoritesRecyclerView.adapter = favoriteAdapter

        loadFavorites()

        return view
    }

    private fun loadFavorites() {
        lifecycleScope.launch {
            val favoriteDao = AppDatabase.getInstance(requireContext()).favoriteDao()
            val favorites = favoriteDao.getFavoritesByUsername(currentUser)
            favoriteAdapter.updateFavorites(favorites)
        }
    }

    private fun removeFavorite(favorite: Favorite) {
        lifecycleScope.launch {
            val favoriteDao = AppDatabase.getInstance(requireContext()).favoriteDao()
            favoriteDao.delete(favorite)
            loadFavorites()
        }
    }
}