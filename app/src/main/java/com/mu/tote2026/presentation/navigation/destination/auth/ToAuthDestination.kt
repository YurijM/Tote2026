package com.mu.tote2026.presentation.navigation.destination.auth

import androidx.navigation.NavController
import com.mu.tote2026.presentation.navigation.Destinations.AuthDestination
import com.mu.tote2026.presentation.navigation.Destinations.SplashDestination

fun NavController.navigateToAuth() {
    /*navigate(AUTH_SCREEN) {
        popUpTo(SPLASH_SCREEN) {
            inclusive = true
        }
    }*/
    navigate(AuthDestination) {
        popUpTo(SplashDestination) {
            inclusive = true
        }
    }
}