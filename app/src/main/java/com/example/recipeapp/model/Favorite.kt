package com.example.recipeapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "meal_id")
    val mealId: String,

    @ColumnInfo(name = "meal_name")
    val mealName: String,

    @ColumnInfo(name = "meal_thumbnail")
    val mealThumbnail: String
)