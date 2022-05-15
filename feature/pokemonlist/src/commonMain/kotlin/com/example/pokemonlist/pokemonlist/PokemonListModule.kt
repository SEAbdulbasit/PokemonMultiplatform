package com.example.pokemonlist.pokemonlist

import com.example.pokemonlist.pokemonlist.domain.GetPokemonListInteractor
import com.example.pokemonlist.pokemonlist.domain.SGetPokemonListInteractorImpl
import com.example.pokemonlist.pokemonlist.presentation.PokemonListViewModel
import com.example.pokemonlist.pokemonlist.presentation.PokemonListViewModelImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val pokemonListModule = DI.Module("pokemonListModule") {

    bind<PokemonListViewModel>() with singleton {
        PokemonListViewModelImpl(
            pokemonListInteractor = instance()
        )
    }

    bind<GetPokemonListInteractor>() with singleton {
        SGetPokemonListInteractorImpl(
            pokemonApi = instance()
        )
    }

}
