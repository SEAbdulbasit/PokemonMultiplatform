package com.example.pokemon.network

import PokemonList
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

private const val BASE_URL = "https://pokeapi.co/api/v2/ability/?limit=20&offset=20"

class PokemonApiClient(
    private val client: HttpClient,
) : PokemonApi {

    override suspend fun fetchPokemonList(limit: Int, offset: Int): PokemonList {
        return client.get("$BASE_URL").body()
    }
}
