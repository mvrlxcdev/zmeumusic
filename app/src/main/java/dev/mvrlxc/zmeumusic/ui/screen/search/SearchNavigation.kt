package dev.mvrlxc.zmeumusic.ui.screen.search

import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import dev.mvrlxc.zmeumusic.data.remote.model.SearchSongsDTO
import dev.mvrlxc.zmeumusic.ui.screen.player.PlayerViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

const val SearchNavigationRoute = "search_route"

fun NavController.navigateSearch(
    navOptions: NavOptions? = null
) {
    this.navigate(SearchNavigationRoute, navOptions)
}

fun NavGraphBuilder.searchScreen(
    onCardClick: ((Pair<Int, List<SearchSongsDTO>>)) -> Unit,
    playerViewModel: PlayerViewModel,

    ) {
    composable(
        SearchNavigationRoute,
        content = {
            SearchScreen(
                viewModel = koinViewModel<SearchViewModel>(),
                onCardClick = onCardClick,
                activeTrackID = playerViewModel.uiState.collectAsState().value.currentTrackData.trackUrl,
                isAbleToPlay = playerViewModel.uiState.collectAsState().value.isAbleToPlay
            )
        },
    )
}