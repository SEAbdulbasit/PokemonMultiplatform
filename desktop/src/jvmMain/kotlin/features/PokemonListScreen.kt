@file:OptIn(ExperimentalFoundationApi::class)

package features

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pokemonlist.pokemonlist.models.Pokemon
import com.example.pokemonlist.pokemonlist.pokemonListModule
import com.example.pokemonlist.pokemonlist.presentation.PokemonListAction
import com.example.pokemonlist.pokemonlist.presentation.PokemonListState
import com.example.pokemonlist.pokemonlist.presentation.PokemonListViewModel
import com.krossovochkin.imageloader.ImageLoader
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
        color = Color.Black, modifier = Modifier.fillMaxSize()
    ) {
        when (pokemonListState) {
            is PokemonListState.Loading -> PokemonLoadingScreen()
            is PokemonListState.Data -> DataState(state = pokemonListState, onAction = { onAction(it) })
            is PokemonListState.Error -> ErrorState(state = pokemonListState)
        }
    }
}

@Composable
private fun PokemonLoadingScreen() {
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
    LazyVerticalGrid(
        cells = GridCells.Adaptive(240.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        items(state.pokemonList) { pokemon ->
            PokemonItem(pokemon = pokemon, onAction = { })
        }
    }
}

@Composable
private fun PokemonItem(pokemon: Pokemon, onAction: (PokemonListAction) -> Unit) {
    ImageLoader.rememberImagePainter(pokemon.getImageUrl())?.let {
        Card(modifier = Modifier.wrapContentSize(), shape = RoundedCornerShape(8.dp)) {
            Image(
                painter = it,
                modifier = Modifier.height(240.dp).width(240.dp),
                contentDescription = pokemon.name,
            )
        }
    }
}

@Composable
private fun ErrorState(
    state: PokemonListState.Error
) {
    Text(text = "${state.error}")
}


