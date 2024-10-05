package dev.mvrlxc.zmeumusic.ui.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.mvrlxc.zmeumusic.data.remote.model.SearchSongsDTO
import dev.mvrlxc.zmeumusic.data.utils.mappers.toTrackData
import dev.mvrlxc.zmeumusic.ui.components.PlayerBottomSheetScaffold
import dev.mvrlxc.zmeumusic.ui.screen.player.PlayerScreen
import dev.mvrlxc.zmeumusic.ui.screen.player.PlayerViewEvent
import dev.mvrlxc.zmeumusic.ui.screen.player.PlayerViewModel
import dev.mvrlxc.zmeumusic.ui.screen.player.navigatePlayer
import dev.mvrlxc.zmeumusic.ui.screen.player.playerScreen
import dev.mvrlxc.zmeumusic.ui.screen.search.SearchNavigationRoute
import dev.mvrlxc.zmeumusic.ui.screen.search.searchScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route
    val currentDestination = navController
        .currentBackStackEntryAsState().value?.destination

    val playerViewModel = koinViewModel<PlayerViewModel>()
    val playerUIState by playerViewModel.uiState.collectAsState()


    PlayerBottomSheetScaffold(
        onClick = {
            navController.navigatePlayer()
            playerViewModel.onTriggerEvent(PlayerViewEvent.OnHideShowMiniPlayer)
        },
        onIconClick = { playerViewModel.onTriggerEvent(PlayerViewEvent.OnPlayIconClick) },
        songName = playerUIState.currentTrackData.name,
        artistName = playerUIState.currentTrackData.artists,
        isPlaying = playerUIState.isPlaying,
        imageUrl = playerUIState.currentTrackData.thumbnailUrlSmall,
        isLoading = playerUIState.isAbleToPlay,
        sheetPeekHeight = if (playerUIState.isMiniPlayerVisible) 80.dp + WindowInsets.systemBars.asPaddingValues()
            .calculateBottomPadding() else 0.dp,
        sheetContent = { PlayerScreen(viewModel = playerViewModel) },
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = SearchNavigationRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            searchScreen(
                playerViewModel = playerViewModel,
                onCardClick = { data ->
                    playerViewModel.onTriggerEvent(
                        PlayerViewEvent.OnPlay(Triple(data.first, data.second.map { it.toTrackData() }, data.third))
                    )
                },
            )
        }
    }
}