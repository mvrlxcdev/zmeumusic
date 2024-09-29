package dev.mvrlxc.zmeumusic.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import dev.mvrlxc.zmeumusic.ui.screen.player.PlayerViewModel
import dev.mvrlxc.zmeumusic.ui.screen.search.SearchViewModel

val viewModelModule = module {
    viewModelOf(::SearchViewModel)
    viewModelOf(::PlayerViewModel)
}