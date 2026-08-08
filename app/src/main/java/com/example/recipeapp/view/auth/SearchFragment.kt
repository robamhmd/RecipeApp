package com.example.recipeapp.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.R
import com.example.recipeapp.view.auth.recipe.SearchRecipeAdapter
import com.example.recipeapp.viewmodel.RecipeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.recipeapp.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val mBinding: FragmentSearchBinding get() = _binding!!
    private val viewModel: RecipeViewModel by viewModels()
    private lateinit var searchAdapter: SearchRecipeAdapter
    private var searchJob: Job? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        mBinding.etSearch.addTextChangedListener { editable ->
            searchJob?.cancel()
            searchJob = MainScope().launch {
                delay(500L)
                editable?.let {
                    if (it.toString().isNotEmpty()) {
                        viewModel.searchRecipes(it.toString())
                    }
                }
            }
        }

        viewModel.searchResult.observe(viewLifecycleOwner) { recipes ->
            searchAdapter.submitList(recipes)
        }
    }

    private fun setupRecyclerView() {
        searchAdapter = SearchRecipeAdapter { recipe ->
            val bundle = Bundle().apply {
                putString("recipeId", recipe.idMeal)
                putString("recipeName", recipe.strMeal)
                putString("recipeImage", recipe.strMealThumb)
                putString("recipeCategory", recipe.strCategory)
            }
            findNavController().navigate(R.id.action_searchFragment_to_recipeDetailFragment, bundle)
        }
        mBinding.rvSearchResults.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}