package com.example.recipeapp.model

data class Ingredient(
    val name: String,
    val measure: String
) {
    val imageUrl: String
        get() = "https://www.themealdb.com/images/ingredients/$name-Small.png"
}