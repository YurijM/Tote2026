package com.mu.tote2026.presentation.navigation.destination.splash

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.navigation.Destinations.SplashRoute
import com.mu.tote2026.presentation.screen.splash.SplashScreen

fun NavGraphBuilder.splash(
    toAuth: () -> Unit,
    toMain: () -> Unit
) {
    /*composable(SPLASH_SCREEN) {
        SplashScreen(
            toAuth = toAuth,
            toMain = toMain
        )
    }*/
    composable<SplashRoute> {
        SplashScreen(
            toAuth = toAuth,
            toMain = toMain
        )
    }
}