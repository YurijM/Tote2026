package com.mu.tote2026.presentation.navigation.destination.splash

import androidx.navigation.NavController
import com.mu.tote2026.presentation.utils.Route.AUTH_SCREEN
import com.mu.tote2026.presentation.utils.Route.SPLASH_SCREEN

fun NavController.navigateToAuth() {
    navigate(AUTH_SCREEN) {
        popUpTo(SPLASH_SCREEN) {
            //inclusive = true
        }
    }
}