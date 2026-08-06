package com.example.recipeapp.model.repository

import com.example.recipeapp.model.User
import com.example.recipeapp.model.UserDao

class UserRepository(private val userDao: UserDao) {

    suspend fun registerUser(user: User) {
        userDao.insert(user)
    }

    suspend fun getUser(username: String): User? {
        return userDao.getUserByUsername(username)
    }
}