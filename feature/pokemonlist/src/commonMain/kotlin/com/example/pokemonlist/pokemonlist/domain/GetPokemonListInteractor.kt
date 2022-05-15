package com.example.pokemonlist.pokemonlist.domain

import com.example.pokemon.network.PokemonApi
import com.example.pokemonlist.pokemonlist.models.Pokemon
import com.example.pokemonlist.pokemonlist.models.toPokemon


interface GetPokemonListInteractor {
    suspend fun getPokemonList(offset: Int): List<Pokemon>
}

internal class SGetPokemonListInteractorImpl(
    private val pokemonApi: PokemonApi
) : GetPokemonListInteractor {

    override suspend fun getPokemonList(offset: Int): List<Pokemon> {
        return pokemonApi.fetchPokemonList(limit = 20, offset = offset).results.map { it.toPokemon() }
    }
}
