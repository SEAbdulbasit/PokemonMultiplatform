package com.example.pokemonlist.pokemonlist.presentation

import com.example.pokemonlist.pokemonlist.domain.GetPokemonListInteractor
import com.krossovochkin.presentation.BaseViewModel
import com.krossovochkin.presentation.ViewModel
import kotlinx.coroutines.launch

interface PokemonListViewModel : ViewModel<PokemonListState, PokemonListAction>

class PokemonListViewModelImpl(
    private val pokemonListInteractor: GetPokemonListInteractor
) : BaseViewModel<PokemonListState, PokemonListAction, PokemonListActionResult>(PokemonListState.Loading),
    PokemonListViewModel {

    init {
        performAction(PokemonListAction.Load)
    }

    override suspend fun reduceState(
        state: PokemonListState, result: PokemonListActionResult
    ): PokemonListState = when (state) {
        PokemonListState.Loading -> {
            when (result) {
                is PokemonListActionResult.Loaded -> PokemonListState.Data(
                    result.pokemonList
                )
            }
        }
        is PokemonListState.Data -> {
            when (result) {
                is PokemonListActionResult.Loaded -> state.copy(result.pokemonList)
            }
        }
        is PokemonListState.Error -> {
            when (result) {
                is PokemonListActionResult.Loaded -> state//.copy(result.pokemonList)
            }
        }
    }

    override fun performAction(action: PokemonListAction) {
        when (action) {
            PokemonListAction.Load -> {
                scope.launch {
                    onActionResult(PokemonListActionResult.Loaded(pokemonListInteractor.getPokemonList(0)))
                }
            }
        }
    }
}
