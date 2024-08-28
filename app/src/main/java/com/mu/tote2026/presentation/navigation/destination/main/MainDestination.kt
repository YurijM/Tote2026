package com.mu.tote2026.presentation.navigation.destination.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.screen.main.MainScreen
import com.mu.tote2026.presentation.utils.Route.MAIN_SCREEN

fun NavGraphBuilder.main(
    toAuth: () -> Unit
) {
    composable(MAIN_SCREEN) {
        MainScreen(
            toAuth = toAuth
        )
    }
}