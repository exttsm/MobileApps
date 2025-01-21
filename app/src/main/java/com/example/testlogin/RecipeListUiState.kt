package com.example.testlogin

data class RecipeListUiState(
    val recipes: List<Recipe> = emptyList(),
    val isLoading: Boolean = false
)