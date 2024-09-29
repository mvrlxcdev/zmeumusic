package dev.mvrlxc.zmeumusic.di

import dev.mvrlxc.zmeumusic.data.repository.YouTubeApiRepositoryImpl
import dev.mvrlxc.zmeumusic.domain.repository.YouTubeApiRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<YouTubeApiRepository> { YouTubeApiRepositoryImpl(httpClient = get()) }
}