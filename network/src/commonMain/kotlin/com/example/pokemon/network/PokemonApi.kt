package com.example.pokemon.network

import PokemonList

interface PokemonApi {

    suspend fun fetchPokemonList(
        limit: Int = 20, offset: Int = 0
    ): PokemonList

}
