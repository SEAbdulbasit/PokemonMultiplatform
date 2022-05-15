package com.example.pokemon.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton


val networkModule = DI.Module("ApiModule") {

    bind<PokemonApi>() with singleton {
        PokemonApiClient(
            client = instance(),
        )
    }

    bind<HttpClient>() with singleton {
        HttpClient() {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
    }
}
