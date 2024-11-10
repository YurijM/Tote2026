package com.mu.tote2026.presentation.navigation.destination.auth

import androidx.navigation.NavController
import com.mu.tote2026.presentation.navigation.Destinations.AuthRoute
import com.mu.tote2026.presentation.navigation.Destinations.SplashRoute

fun NavController.navigateToAuth() {
    /*navigate(AUTH_SCREEN) {
        popUpTo(SPLASH_SCREEN) {
            inclusive = true
        }
    }*/
    navigate(AuthRoute) {
        popUpTo(SplashRoute) {
            inclusive = true
        }
    }
}