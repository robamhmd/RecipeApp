package com.example.recipeapp.view.auth.recipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.databinding.ItemSearchRecipeBinding
import com.example.recipeapp.model.remote.Recipe

class SearchRecipeAdapter(private val onItemClick: (Recipe) -> Unit) :
    ListAdapter<Recipe, SearchRecipeAdapter.SearchViewHolder>(RecipeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemSearchRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SearchViewHolder(private val binding: ItemSearchRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe) {
            binding.tvRecipeName.text = recipe.strMeal
            binding.tvRecipeCategory.text = recipe.strCategory

            Glide.with(binding.root.context)
                .load(recipe.strMealThumb)
                .into(binding.ivRecipeImage)

            binding.root.setOnClickListener { onItemClick(recipe) }
        }
    }

    class RecipeDiffCallback : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe) = oldItem.idMeal == newItem.idMeal
        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe) = oldItem == newItem
    }
}