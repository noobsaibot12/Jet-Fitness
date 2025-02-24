package com.example.jetfitnessapp.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object KtorClientFactory {

    val client: HttpClient = HttpClient( CIO ) {

        install( ContentNegotiation ) {

            json(

                Json {

                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true

                }

            )

        }

        install( Logging ) {

            level = LogLevel.ALL

        }

    }

}