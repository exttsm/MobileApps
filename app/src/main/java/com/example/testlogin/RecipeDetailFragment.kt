package com.example.testlogin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class RecipeDetailFragment : Fragment() {
    private var pokemonId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pokemonId = it.getInt(ARG_POKEMON_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pokemon = getPokemonById(pokemonId)

        pokemon?.let {
            view.apply {
                findViewById<ImageView>(R.id.detailImageView).setImageResource(it.imageResId)
                findViewById<TextView>(R.id.detailTitleTextView).text = it.title
                findViewById<TextView>(R.id.detailDescriptionTextView).text = it.detailDescription
            }
        }
    }

    private fun getPokemonById(id: Int): Recipe? {
        return when (id) {
            1 -> Recipe(
                1,
                "Charmander",
                "",
                R.drawable.ic_charmander,
                false,
                "Charmander (Japanese: ヒトカゲ Hitokage) is a Fire-type Pokémon introduced in Generation I."
            )
            2 -> Recipe(
                2,
                "Charmeleon",
                "",
                R.drawable.ic_charmeleon,
                false,
                "Charmeleon (Japanese: リザード Lizardo) is a Fire-type Pokémon introduced in Generation I."
            )
            3 -> Recipe(
                3,
                "Charizard",
                "",
                R.drawable.ic_charizard,
                false,
                "Charizard (Japanese: リザードン Lizardon) is a dual-type Fire/Flying Pokémon introduced in Generation I."
            )

            else -> null
        }
    }

    companion object {
        private const val ARG_POKEMON_ID = "pokemon_id"

        fun newInstance(pokemonId: Int) = RecipeDetailFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_POKEMON_ID, pokemonId)
            }
        }
    }
}