package com.mu.tote2026.presentation.navigation.destination.main

import androidx.navigation.NavController
import com.mu.tote2026.presentation.utils.Route.MAIN_SCREEN
import com.mu.tote2026.presentation.utils.Route.SPLASH_SCREEN

fun NavController.navigateToMain() {
    navigate(MAIN_SCREEN) {
        popUpTo(SPLASH_SCREEN) {
            inclusive = true
        }
    }
}