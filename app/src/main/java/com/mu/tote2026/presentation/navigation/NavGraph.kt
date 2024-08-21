package com.mu.tote2026.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.mu.tote2026.presentation.navigation.destination.auth.signIn
import com.mu.tote2026.presentation.navigation.destination.splash.navigateToAuth
import com.mu.tote2026.presentation.navigation.destination.splash.splash
import com.mu.tote2026.presentation.navigation.destination.test.test
import com.mu.tote2026.presentation.utils.Route.SPLASH_SCREEN

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = SPLASH_SCREEN
    ) {
        test()
        splash(
            toAuth = {
                navController.navigateToAuth()
            },
        )
        signIn()
    }
}