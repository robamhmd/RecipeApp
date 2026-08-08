package com.example.recipeapp.view.auth.recipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.databinding.ItemIngredientBinding
import com.example.recipeapp.model.Ingredient

class IngredientAdapter(private var ingredientsList: List<Ingredient> = emptyList()) :
    RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {

    fun submitList(list: List<Ingredient>) {
        ingredientsList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val binding = ItemIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = ingredientsList[position]
        holder.binding.apply {
            tvIngredientName.text = ingredient.name
            tvIngredientAmount.text = ingredient.measure

            Glide.with(ivIngredient.context)
                .load(ingredient.imageUrl)
                .into(ivIngredient)
        }
    }

    override fun getItemCount(): Int = ingredientsList.size

    class IngredientViewHolder(val binding: ItemIngredientBinding) : RecyclerView.ViewHolder(binding.root)
}