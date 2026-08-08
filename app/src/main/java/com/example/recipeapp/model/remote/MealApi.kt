package com.example.recipeapp.model.remote


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("search.php")
    suspend fun getHomeRecipes(@Query("f") firstLetter: String = "b"): Response<RecipeResponse>

    @GET("search.php")
    suspend fun searchRecipes(@Query("s") query: String): Response<RecipeResponse>

    @GET("lookup.php")
    suspend fun getRecipeDetailsById(@Query("i") id: String): RecipeResponse

    @GET("filter.php")
    suspend fun getRecipesByCategory(@Query("c") category: String): Response<RecipeResponse>
}