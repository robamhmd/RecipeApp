package com.example.recipeapp.view.auth.recipe

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentRecipeDetailBinding
import com.example.recipeapp.model.Ingredient
import com.example.recipeapp.model.remote.Recipe
import com.example.recipeapp.viewmodel.RecipeViewModel

class RecipeDetailFragment : Fragment() {

    private var _binding: FragmentRecipeDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecipeViewModel by viewModels()
    private lateinit var ingredientAdapter: IngredientAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRecipeDetailBinding.bind(view)

        setupRecyclerView()

        val recipeId = arguments?.getString("recipeId")
        val recipeName = arguments?.getString("recipeName")
        val recipeImage = arguments?.getString("recipeImage")

        binding.tvDetailTitle.text = recipeName
        Glide.with(this).load(recipeImage).into(binding.ivDetailImage)

        recipeId?.let {
            viewModel.getRecipeDetails(it)
        }

        viewModel.recipeDetails.observe(viewLifecycleOwner) { recipe ->
            if (recipe != null) {
                binding.tvDetailDesc.text = recipe.strInstructions
                binding.tvDetailTag.text = recipe.strCategory

                val ingredientsList = extractIngredients(recipe)
                ingredientAdapter.submitList(ingredientsList)

                binding.cvPlayVideo.setOnClickListener {
                    val youtubeUrl = recipe.strYoutube
                    if (!youtubeUrl.isNullOrEmpty()) {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
                        startActivity(intent)
                    }
                }
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupRecyclerView() {
        ingredientAdapter = IngredientAdapter()
        binding.rvIngredients.apply {
            adapter = ingredientAdapter
            layoutManager = LinearLayoutManager(requireContext())
            isNestedScrollingEnabled = false
        }
    }

    private fun extractIngredients(recipe: Recipe): List<Ingredient> {
        val list = mutableListOf<Ingredient>()

        fun addToList(name: String?, measure: String?) {
            if (!name.isNullOrBlank()) {
                list.add(Ingredient(name, measure ?: ""))
            }
        }

        addToList(recipe.strIngredient1, recipe.strMeasure1)
        addToList(recipe.strIngredient2, recipe.strMeasure2)
        addToList(recipe.strIngredient3, recipe.strMeasure3)
        addToList(recipe.strIngredient4, recipe.strMeasure4)
        addToList(recipe.strIngredient5, recipe.strMeasure5)
        addToList(recipe.strIngredient6, recipe.strMeasure6)
        addToList(recipe.strIngredient7, recipe.strMeasure7)
        addToList(recipe.strIngredient8, recipe.strMeasure8)
        addToList(recipe.strIngredient9, recipe.strMeasure9)
        addToList(recipe.strIngredient10, recipe.strMeasure10)
        addToList(recipe.strIngredient11, recipe.strMeasure11)
        addToList(recipe.strIngredient12, recipe.strMeasure12)
        addToList(recipe.strIngredient13, recipe.strMeasure13)
        addToList(recipe.strIngredient14, recipe.strMeasure14)
        addToList(recipe.strIngredient15, recipe.strMeasure15)
        addToList(recipe.strIngredient16, recipe.strMeasure16)
        addToList(recipe.strIngredient17, recipe.strMeasure17)
        addToList(recipe.strIngredient18, recipe.strMeasure18)
        addToList(recipe.strIngredient19, recipe.strMeasure19)
        addToList(recipe.strIngredient20, recipe.strMeasure20)

        return list
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}