package com.darkndev.ktorauth.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KtorAuthModule {

    @Singleton
    @Provides
    fun provideHttpClient() = HttpClient(OkHttp) {
        expectSuccess = true
        install(Logging) {
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json()
        }
    }

}