package com.example.pokemonlist.pokemonlist.models

import Result

data class Pokemon(val name: String, val url: String) {
    fun getImageUrl(): String {
        val index = url.split("/".toRegex()).dropLast(1).last()
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$index.png"
    }
}


fun Result.toPokemon(): Pokemon = Pokemon(
    name = this.name, url = this.url
)