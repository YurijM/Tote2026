package com.mu.tote2026.presentation.navigation.destination.main

import androidx.navigation.NavController
import com.mu.tote2026.presentation.utils.Route.AUTH_SCREEN
import com.mu.tote2026.presentation.utils.Route.MAIN_SCREEN

fun NavController.navigateToMain() {
    navigate(MAIN_SCREEN) {
        popUpTo(AUTH_SCREEN) {
            inclusive = true
        }
    }
}