package com.example.pokemonlist.pokemonlist.presentation

import PokemonList
import com.example.pokemonlist.pokemonlist.models.Pokemon

sealed class PokemonListAction {

    object Load : PokemonListAction()
    object Back : PokemonListAction()
}

sealed class PokemonListActionResult {

    data class Loaded(
        val pokemonList: List<Pokemon>
    ) : PokemonListActionResult()

}
