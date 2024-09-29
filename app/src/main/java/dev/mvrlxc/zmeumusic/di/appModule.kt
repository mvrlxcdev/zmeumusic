package dev.mvrlxc.zmeumusic.di

import dev.mvrlxc.zmeumusic.data.player.MediaPlayerControllerImpl
import dev.mvrlxc.zmeumusic.domain.player.MediaPlayerController
import org.koin.dsl.module

val appModule = module {
    single<MediaPlayerController> { MediaPlayerControllerImpl(get()) }
}

val appModules = listOf(remoteModule, repositoryModule, viewModelModule, appModule)




