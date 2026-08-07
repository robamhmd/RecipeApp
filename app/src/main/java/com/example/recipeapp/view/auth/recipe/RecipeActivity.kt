package com.example.recipeapp.view.recipe

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.recipeapp.R
import com.example.recipeapp.utils.SharedPrefManager
import com.example.recipeapp.view.auth.AuthActivity

class RecipeActivity : AppCompatActivity() {

    private lateinit var sharedPrefManager: SharedPrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        sharedPrefManager = SharedPrefManager(this)

        val btnLogout = findViewById<Button>(R.id.btnLogout)

        btnLogout.setOnClickListener {

            sharedPrefManager.logout()

            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)

            finish()
        }
    }
}