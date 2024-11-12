package com.mu.tote2026.presentation.navigation.destination.main

import androidx.navigation.NavController
import com.mu.tote2026.presentation.navigation.Destinations.MainDestination
import com.mu.tote2026.presentation.navigation.Destinations.SplashDestination

fun NavController.navigateToMain() {
    /*navigate(MAIN_SCREEN) {
        popUpTo(SPLASH_SCREEN) {
            inclusive = true
        }
    }*/
    navigate(MainDestination) {
        popUpTo(SplashDestination) {
            inclusive = true
        }
    }
}