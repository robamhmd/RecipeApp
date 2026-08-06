package com.example.recipeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.model.User
import com.example.recipeapp.model.repository.UserRepository
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {

    fun register(user: User) {
        viewModelScope.launch {
            repository.registerUser(user)
        }
    }

    suspend fun login(username: String): User? {
        return repository.getUser(username)
    }
    suspend fun userExists(username: String): Boolean {
        return repository.userExists(username)
    }
}