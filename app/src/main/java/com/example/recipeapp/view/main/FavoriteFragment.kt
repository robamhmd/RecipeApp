package com.example.recipeapp.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R

class FavoriteFragment : Fragment() {

    private lateinit var favoritesRecyclerView: RecyclerView
    private lateinit var favoriteAdapter: FavoriteAdapter

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

        favoriteAdapter = FavoriteAdapter(
            emptyList()
        ) {

        }

        favoritesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        favoritesRecyclerView.adapter = favoriteAdapter

        return view
    }
}