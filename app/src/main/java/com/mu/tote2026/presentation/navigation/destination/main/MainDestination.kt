package com.mu.tote2026.presentation.navigation.destination.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.navigation.Destinations.MainRoute
import com.mu.tote2026.presentation.screen.main.MainScreen

fun NavGraphBuilder.main(
    toAuth: () -> Unit,
    toProfile: () -> Unit
) {
    /*composable(MAIN_SCREEN) {
        MainScreen(
            toAuth = toAuth,
            toProfile = toProfile,
        )
    }*/
    composable<MainRoute> {
        MainScreen(
            toAuth = toAuth,
            toProfile = toProfile,
        )
    }
}