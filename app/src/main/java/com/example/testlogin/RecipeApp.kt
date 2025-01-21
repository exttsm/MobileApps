package com.example.testlogin

import android.app.Application

class RecipeApp : Application() {
    val credentialsManager: CredentialsManager by lazy {
        CredentialsManager()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private lateinit var instance: RecipeApp
        fun getInstance(): RecipeApp = instance
    }
}