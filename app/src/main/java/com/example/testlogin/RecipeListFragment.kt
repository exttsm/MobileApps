package com.example.testlogin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class RecipeListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private val recipeAdapter = RecipeAdapter { recipe, action ->
        when (action) {
            RecipeAction.ITEM_CLICK -> {
                Toast.makeText(context, "Clicked recipe id: ${recipe.id}", Toast.LENGTH_SHORT).show()
                // Navigate to details
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, RecipeDetailFragment.newInstance(recipe.id))
                    .addToBackStack(null)
                    .commit()
            }
            RecipeAction.SHARE -> {
                Toast.makeText(context, "Sharing Pokemon id: ${recipe.id}", Toast.LENGTH_SHORT).show()
            }
            RecipeAction.FAVORITE -> {
                Toast.makeText(context, "Favorited Pokemon id: ${recipe.id}", Toast.LENGTH_SHORT).show()
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

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = recipeAdapter

        // Add sample data
        recipeAdapter.submitList(getSampleRecipes())
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