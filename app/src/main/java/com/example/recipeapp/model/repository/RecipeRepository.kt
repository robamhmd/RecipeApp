package com.example.recipeapp.model.repository

import com.example.recipeapp.model.remote.RecipeResponse
import com.example.recipeapp.model.remote.RetrofitClient
import retrofit2.Response

class RecipeRepository {
    suspend fun getHomeRecipes(letter: String = "b"): Response<RecipeResponse> {
        return RetrofitClient.api.getHomeRecipes(letter)
    }

    suspend fun searchRecipes(query: String): Response<RecipeResponse> {
        return RetrofitClient.api.searchRecipes(query)
    }

    suspend fun getRecipesByCategory(category: String): Response<RecipeResponse> {
        return RetrofitClient.api.getRecipesByCategory(category)
    }
}