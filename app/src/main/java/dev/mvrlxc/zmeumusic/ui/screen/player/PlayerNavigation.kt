package dev.mvrlxc.zmeumusic.ui.screen.player

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val PlayerNavigationRoute = "player_route"

fun NavController.navigatePlayer(
    navOptions: NavOptions? = null,
) {
    this.navigate(PlayerNavigationRoute, navOptions)
}

fun NavGraphBuilder.playerScreen(viewModel: PlayerViewModel) {
    composable(
        PlayerNavigationRoute,
        content = {
            PlayerScreen(
                viewModel = viewModel
            )
        }
    )
}