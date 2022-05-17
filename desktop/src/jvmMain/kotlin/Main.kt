// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.foundation.LocalScrollbarStyle
import androidx.compose.foundation.ScrollbarStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.pokemon.network.networkModule
import features.PokemonListScreenListScreen
import org.kodein.di.DI


fun main() = application {
    Window(
        onCloseRequest = ::exitApplication
    ) {
        MaterialTheme {
            CompositionLocalProvider(
                LocalScrollbarStyle provides ScrollbarStyle(
                    minimalHeight = 16.dp,
                    thickness = 8.dp,
                    shape = MaterialTheme.shapes.small,
                    hoverDurationMillis = 300,
                    unhoverColor = MaterialTheme.colors.onSurface.copy(alpha = 0.12f),
                    hoverColor = MaterialTheme.colors.onSurface.copy(alpha = 0.50f)
                )
            ) {

                val di = remember {
                    DI {
                        import(networkModule)
                    }
                }
                PokemonListScreenListScreen(di)
            }
        }
    }
}
