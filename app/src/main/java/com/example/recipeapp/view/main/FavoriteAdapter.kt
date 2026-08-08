package com.example.recipeapp.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.model.Favorite

class FavoriteAdapter(
    private var favorites: List<Favorite>,
    private val onDeleteClick: (Favorite) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val mealImage: ImageView = itemView.findViewById(R.id.mealImage)
        val mealName: TextView = itemView.findViewById(R.id.mealName)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorite, parent, false)

        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: FavoriteViewHolder,
        position: Int
    ) {
        val favorite = favorites[position]

        holder.mealName.text = favorite.mealName

        holder.deleteButton.setOnClickListener {
            onDeleteClick(favorite)
        }
    }

    override fun getItemCount(): Int {
        return favorites.size
    }

    fun updateFavorites(newFavorites: List<Favorite>) {
        favorites = newFavorites
        notifyDataSetChanged()
    }
}