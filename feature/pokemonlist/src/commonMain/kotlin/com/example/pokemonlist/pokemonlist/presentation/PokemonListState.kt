package com.example.pokemonlist.pokemonlist.presentation

import com.example.pokemonlist.pokemonlist.models.Pokemon

sealed class PokemonListState {

    object Loading : PokemonListState()

    data class Data(
        val pokemonList: List<Pokemon>,
    ) : PokemonListState()

    data class Error(
        val error: Throwable
    ) : PokemonListState()
}
