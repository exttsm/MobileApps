package com.example.testlogin

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RecipeListViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RecipeListUiState())
    val uiState: StateFlow<RecipeListUiState> = _uiState.asStateFlow()

    private var allRecipes: List<Recipe> = emptyList()

    fun setRecipes(recipes: List<Recipe>) {
        allRecipes = recipes
        _uiState.update { currentState ->
            currentState.copy(
                recipes = filterRecipes(currentState.searchQuery)
            )
        }
    }

    fun onSearchQueryChanged(query: String) {
        // Only update if the query is empty or has 3+ characters
        if (query.isEmpty() || query.length >= 3) {
            _uiState.update { currentState ->
                // Only update if the filtered results would be different
                val filteredRecipes = filterRecipes(query)
                if (filteredRecipes != currentState.recipes) {
                    currentState.copy(
                        searchQuery = query,
                        recipes = filteredRecipes
                    )
                } else {
                    currentState.copy(searchQuery = query)
                }
            }
        }
    }

    private fun filterRecipes(query: String): List<Recipe> {
        return if (query.length < 3) {
            allRecipes
        } else {
            val lowerCaseQuery = query.lowercase()
            allRecipes.filter { recipe ->
                recipe.title.lowercase().contains(lowerCaseQuery) ||
                        recipe.description.lowercase().contains(lowerCaseQuery)
            }
        }
    }
}