package com.example.recipeapp.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDao {

    @Insert
    suspend fun insert(favorite: Favorite)

    @Query("SELECT * FROM favorites WHERE username = :username")
    suspend fun getFavoritesByUsername(username: String): List<Favorite>

    @Delete
    suspend fun delete(favorite: Favorite)
}