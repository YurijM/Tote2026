package com.mu.tote2026.presentation.navigation.destination.main

import androidx.navigation.NavController
import com.mu.tote2026.presentation.navigation.Destinations.MainRoute
import com.mu.tote2026.presentation.navigation.Destinations.SplashRoute

fun NavController.navigateToMain() {
    /*navigate(MAIN_SCREEN) {
        popUpTo(SPLASH_SCREEN) {
            inclusive = true
        }
    }*/
    navigate(MainRoute) {
        popUpTo(SplashRoute) {
            inclusive = true
        }
    }
}