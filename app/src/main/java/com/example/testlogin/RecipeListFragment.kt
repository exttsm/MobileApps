package com.example.testlogin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.CircularProgressIndicator
import android.widget.Button
import kotlinx.coroutines.launch

class RecipeListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var progressIndicator: CircularProgressIndicator
    private lateinit var logoutButton: Button

    private val viewModel: RecipeListViewModel by viewModels()
    private val credentialsManager: CredentialsManager by lazy {
        RecipeApp.getInstance().credentialsManager
    }

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

        setupViews(view)
        setupStateObserver()
        observeLoginState()

        // Initialize with sample data
        viewModel.setRecipes(RecipeListFragment.getSampleRecipes())
    }

    private fun setupViews(view: View) {
        // Initialize views
        recyclerView = view.findViewById(R.id.recyclerView)
        searchView = view.findViewById(R.id.searchView)
        progressIndicator = view.findViewById(R.id.progressIndicator)
        logoutButton = view.findViewById(R.id.logoutButton)

        // Setup RecyclerView
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recipeAdapter
        }

        // Setup SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.onSearchQueryChanged(newText ?: "")
                return true
            }
        })

        // Setup Logout Button
        logoutButton.setOnClickListener {
            credentialsManager.logout()
        }
    }

    private fun setupStateObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    updateUI(uiState)
                }
            }
        }
    }

    private fun updateUI(uiState: RecipeListUiState) {
        progressIndicator.isVisible = uiState.isLoading
        recyclerView.isVisible = !uiState.isLoading
        recipeAdapter.submitList(uiState.recipes)
    }

    private fun observeLoginState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                credentialsManager.loginState.collect { isLoggedIn ->
                    if (!isLoggedIn) {
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainer, LoginFragment.newInstance(credentialsManager))
                            .commit()
                    }
                }
            }
        }
    }

    companion object {
        private fun getSampleRecipes(): List<Recipe> {
            return listOf(
                Recipe(1, "Charmander", "Lizard Pokemon", R.drawable.ic_charmander, false, ""),
                Recipe(2, "Charmeleon", "Flame Pokemon", R.drawable.ic_charmeleon, false, ""),
                Recipe(3, "Charizard", "Flame Pokemon", R.drawable.ic_charizard, false, "")
            )
        }

        fun newInstance() = RecipeListFragment()
    }
}