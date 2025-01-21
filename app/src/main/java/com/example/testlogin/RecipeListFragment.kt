package com.example.testlogin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class RecipeListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView

    private val viewModel: RecipeListViewModel by viewModels()

    private val recipeAdapter = RecipeAdapter { recipe, action ->
        when (action) {
            RecipeAction.ITEM_CLICK -> {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, RecipeDetailFragment.newInstance(recipe.id))
                    .addToBackStack(null)
                    .commit()
            }
            RecipeAction.SHARE -> {
                // Handle share action
            }
            RecipeAction.FAVORITE -> {
                // Handle favorite action
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView(view)
        setupSearchView(view)
        setupStateObserver()

        // Initialize with sample data
        viewModel.setRecipes(getSampleRecipes())
    }

    private fun setupRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = recipeAdapter
    }

    private fun setupSearchView(view: View) {
        searchView = view.findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.onSearchQueryChanged(newText ?: "")
                return true
            }
        })
    }

    private fun setupStateObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    recipeAdapter.submitList(uiState.recipes)
                }
            }
        }
    }

    private fun getSampleRecipes(): List<Recipe> {
        return listOf(
            Recipe(1, "Charmander", "Lizard Pokemon", R.drawable.ic_charmander, false, ""),
            Recipe(2, "Charmeleon", "Flame Pokemon", R.drawable.ic_charmeleon, false, ""),
            Recipe(3, "Charizard", "Flame Pokemon", R.drawable.ic_charizard, false, "")
        )
    }

    companion object {
        fun newInstance() = RecipeListFragment()
    }
}