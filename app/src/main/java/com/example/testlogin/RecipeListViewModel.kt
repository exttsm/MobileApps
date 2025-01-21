package com.example.testlogin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecipeListViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RecipeListUiState())
    val uiState: StateFlow<RecipeListUiState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    fun setRecipes(recipes: List<Recipe>) {
        viewModelScope.launch {
            _uiState.value = RecipeListUiState(isLoading = true)
            delay(2000) // Simulate network delay
            _uiState.value = RecipeListUiState(recipes = recipes)
        }
    }

    fun onSearchQueryChanged(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            delay(300) // Debounce typing
            delay(2000) // Simulate network delay

            val currentRecipes = _uiState.value.recipes
            val filteredRecipes = if (query.isEmpty()) {
                currentRecipes
            } else {
                currentRecipes.filter { recipe ->
                    recipe.title.contains(query, ignoreCase = true)
                }
            }

            _uiState.value = _uiState.value.copy(
                isLoading = false,
                recipes = filteredRecipes
            )
        }
    }
}
