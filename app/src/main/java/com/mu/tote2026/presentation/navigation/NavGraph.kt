package com.mu.tote2026.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.mu.tote2026.presentation.navigation.destination.auth.signIn
import com.mu.tote2026.presentation.navigation.destination.splash.splash
import com.mu.tote2026.presentation.utils.Route.SIGN_IN_SCREEN

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = SIGN_IN_SCREEN
    ) {
        splash()
        signIn()
    }
}