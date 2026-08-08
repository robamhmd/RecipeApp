package com.example.recipeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.model.remote.Recipe
import com.example.recipeapp.model.remote.RetrofitClient
import com.example.recipeapp.model.repository.RecipeRepository
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {
    private val repository = RecipeRepository()

    private val _homeRecipes = MutableLiveData<List<Recipe>>()
    val homeRecipes: LiveData<List<Recipe>> get() = _homeRecipes

    private val _searchResult = MutableLiveData<List<Recipe>>()
    val searchResult: LiveData<List<Recipe>> get() = _searchResult

    private val _recommendations = MutableLiveData<List<Recipe>>()
    val recommendations: LiveData<List<Recipe>> get() = _recommendations

    fun fetchHomeRecipes() {
        viewModelScope.launch {
            try {
                val response = repository.getHomeRecipes()
                if (response.isSuccessful) {
                    _homeRecipes.value = response.body()?.meals ?: emptyList()
                }
            } catch (e: Exception) {
            }
        }
    }

    fun fetchRecommendations() {
        viewModelScope.launch {
            try {
                val response = repository.getHomeRecipes("c")
                if (response.isSuccessful) {
                    _recommendations.value = response.body()?.meals ?: emptyList()
                }
            } catch (e: Exception) {
            }
        }
    }

    fun searchRecipes(query: String) {
        viewModelScope.launch {
            try {
                val response = repository.searchRecipes(query)
                if (response.isSuccessful) {
                    _searchResult.value = response.body()?.meals ?: emptyList()
                }
            } catch (e: Exception) {
            }
        }
    }

    fun getRecipesByCategory(category: String) {
        viewModelScope.launch {
            try {
                val response = repository.getRecipesByCategory(category)
                if (response.isSuccessful) {
                    _homeRecipes.value = response.body()?.meals ?: emptyList()
                }
            } catch (e: Exception) {
            }
        }
    }

    val recipeDetails = MutableLiveData<Recipe>()

    fun getRecipeDetails(id: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.getRecipeDetailsById(id)
                response.meals?.firstOrNull()?.let { recipe ->
                    recipeDetails.postValue(recipe)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}