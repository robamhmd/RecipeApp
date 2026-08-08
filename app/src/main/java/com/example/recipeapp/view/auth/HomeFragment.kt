package com.example.recipeapp.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentHomeBinding
import com.example.recipeapp.utils.SharedPrefManager
import com.example.recipeapp.view.auth.recipe.RecipeAdapter
import com.example.recipeapp.view.auth.recipe.SearchRecipeAdapter
import com.example.recipeapp.viewmodel.RecipeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val mBinding: FragmentHomeBinding get() = _binding!!

    private val viewModel: RecipeViewModel by viewModels()
    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var recommendationAdapter: SearchRecipeAdapter
    private lateinit var sharedPrefManager: SharedPrefManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPrefManager = SharedPrefManager(requireContext())
        val userName = sharedPrefManager.getFirstName()
        if (userName.isNotEmpty()) {
            mBinding.tvGreetingSub.text = getString(R.string.good_morning_name, userName)
        }

        setupRecyclerView()

        mBinding.tvSearchBar.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }

        viewModel.homeRecipes.observe(viewLifecycleOwner) { recipes ->
            recipeAdapter.submitList(recipes)
        }

        viewModel.recommendations.observe(viewLifecycleOwner) { recipes ->
            recommendationAdapter.submitList(recipes)
        }

        viewModel.fetchHomeRecipes()
        viewModel.fetchRecommendations()
    }

    private fun setupRecyclerView() {

        mBinding.cvCategoryAll.setOnClickListener {
            updateCategorySelection("All")
            viewModel.fetchHomeRecipes()
        }

        mBinding.cvCategoryBreakfast.setOnClickListener {
            updateCategorySelection("Breakfast")
            viewModel.getRecipesByCategory("Breakfast")
        }

        mBinding.cvCategoryLunch.setOnClickListener {
            updateCategorySelection("Lunch")
            viewModel.getRecipesByCategory("Seafood")
        }

        mBinding.cvCategoryDinner.setOnClickListener {
            updateCategorySelection("Dinner")
            viewModel.getRecipesByCategory("Pasta")
        }

        recipeAdapter = RecipeAdapter { recipe ->
            val bundle = Bundle().apply {
                putString("recipeId", recipe.idMeal)
                putString("recipeName", recipe.strMeal)
                putString("recipeImage", recipe.strMealThumb)
                putString("recipeCategory", recipe.strCategory)
            }
            findNavController().navigate(R.id.action_homeFragment_to_recipeDetailFragment, bundle)
        }

        recommendationAdapter = SearchRecipeAdapter { recipe ->
            val bundle = Bundle().apply {
                putString("recipeId", recipe.idMeal)
                putString("recipeName", recipe.strMeal)
                putString("recipeImage", recipe.strMealThumb)
                putString("recipeCategory", recipe.strCategory)
            }
            findNavController().navigate(R.id.action_homeFragment_to_recipeDetailFragment, bundle)
        }

        mBinding.rvHomeRecipes.apply {
            adapter = recipeAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        mBinding.rvRecommendations.apply {
            adapter = recommendationAdapter
            layoutManager = LinearLayoutManager(requireContext())
            isNestedScrollingEnabled = false
        }
    }

    private fun updateCategorySelection(selected: String) {
        val selectedBg = android.graphics.Color.parseColor("#292C3D")
        val unselectedBg = android.graphics.Color.parseColor("#FFFFFF")
        val selectedText = android.graphics.Color.parseColor("#FFFFFF")
        val unselectedText = android.graphics.Color.parseColor("#1D1E2C")

        mBinding.cvCategoryAll.setCardBackgroundColor(unselectedBg)
        mBinding.tvCategoryAll.setTextColor(unselectedText)

        mBinding.cvCategoryBreakfast.setCardBackgroundColor(unselectedBg)
        mBinding.tvCategoryBreakfast.setTextColor(unselectedText)

        mBinding.cvCategoryLunch.setCardBackgroundColor(unselectedBg)
        mBinding.tvCategoryLunch.setTextColor(unselectedText)

        mBinding.cvCategoryDinner.setCardBackgroundColor(unselectedBg)
        mBinding.tvCategoryDinner.setTextColor(unselectedText)

        when (selected) {
            "All" -> {
                mBinding.cvCategoryAll.setCardBackgroundColor(selectedBg)
                mBinding.tvCategoryAll.setTextColor(selectedText)
            }
            "Breakfast" -> {
                mBinding.cvCategoryBreakfast.setCardBackgroundColor(selectedBg)
                mBinding.tvCategoryBreakfast.setTextColor(selectedText)
            }
            "Lunch" -> {
                mBinding.cvCategoryLunch.setCardBackgroundColor(selectedBg)
                mBinding.tvCategoryLunch.setTextColor(selectedText)
            }
            "Dinner" -> {
                mBinding.cvCategoryDinner.setCardBackgroundColor(selectedBg)
                mBinding.tvCategoryDinner.setTextColor(selectedText)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}