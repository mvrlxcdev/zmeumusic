package dev.mvrlxc.zmeumusic.di

import dev.mvrlxc.zmeumusic.utils.Constants.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.plugins.addDefaultResponseValidation
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val remoteModule = module {
    single {
        HttpClient {
            expectSuccess = true
            addDefaultResponseValidation()
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTP
                    host = BASE_URL
                }
            }
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(Logging){
                level = LogLevel.ALL
                logger.log("api")
            }
        }
    }
}