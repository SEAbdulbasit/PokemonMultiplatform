package com.example.pokemonlist.pokemonlist.models

import Result

data class Pokemon(val name: String, val url: String)


fun Result.toPokemon(): Pokemon = Pokemon(
    name = this.name, url = this.url
)