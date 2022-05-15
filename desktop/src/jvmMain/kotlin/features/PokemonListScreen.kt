package features

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokemonlist.pokemonlist.models.Pokemon
import com.example.pokemonlist.pokemonlist.pokemonListModule
import com.example.pokemonlist.pokemonlist.presentation.PokemonListAction
import com.example.pokemonlist.pokemonlist.presentation.PokemonListState
import com.example.pokemonlist.pokemonlist.presentation.PokemonListViewModel
import org.kodein.di.DI
import org.kodein.di.instance


/**
 * Created by abdulbasit on 15/05/2022.
 */

@Composable
fun PokemonListScreenListScreen(parentDi: DI) {
    val di = remember {
        DI {
            extend(parentDi)
            import(pokemonListModule)
        }
    }
    val pokemonListViewModel: PokemonListViewModel by di.instance()

    val pokemonListState = pokemonListViewModel.observeState().collectAsState(PokemonListState.Loading).value
    PokemonListScreenImpl(
        pokemonListState, pokemonListViewModel::performAction, pokemonListViewModel::dispose
    )
}

@Composable
private fun PokemonListScreenImpl(
    pokemonListState: PokemonListState, onAction: (PokemonListAction) -> Unit, onDispose: () -> Unit
) {
    DisposableEffect(null) { onDispose { onDispose() } }
    Surface(
        color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()
    ) {
        when (pokemonListState) {
            is PokemonListState.Loading -> CityListLoadingState()
            is PokemonListState.Data -> DataState(state = pokemonListState, onAction = { onAction(it) })
            is PokemonListState.Error -> ErrorState(state = pokemonListState)
        }
    }
}

@Composable
private fun CityListLoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun DataState(
    state: PokemonListState.Data, onAction: (PokemonListAction) -> Unit
) {
    Column {
        LazyColumn(modifier = Modifier.weight(1f)) {
            itemsIndexed(state.pokemonList) { index, pokemon ->
                PokemonItem(pokemon = pokemon, onAction = { })
            }
        }
    }
}

@Composable
private fun PokemonItem(pokemon: Pokemon, onAction: (PokemonListAction) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth()
        // .clickable(onClick = { onAction(CityListAction.SelectCity(city)) }),
    ) {
        Text(
            modifier = Modifier.padding(16.dp), text = pokemon.name, style = MaterialTheme.typography.body1
        )
    }
}

@Composable
private fun ErrorState(
    state: PokemonListState.Error
) {
    Text(text = "${state.error}")
}


