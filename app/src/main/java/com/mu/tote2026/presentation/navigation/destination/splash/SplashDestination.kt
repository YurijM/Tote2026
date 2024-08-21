package com.mu.tote2026.presentation.navigation.destination.splash

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.screen.splash.SplashScreen
import com.mu.tote2026.presentation.utils.Route.SPLASH_SCREEN

fun NavGraphBuilder.splash(
    toAuth: () -> Unit
) {
    composable(SPLASH_SCREEN) {
        SplashScreen(
            toAuth = toAuth
        )
    }
}