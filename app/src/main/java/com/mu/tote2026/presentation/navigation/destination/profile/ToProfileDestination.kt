package com.mu.tote2026.presentation.navigation.destination.profile

import androidx.navigation.NavController
import com.mu.tote2026.presentation.utils.Route.AUTH_SCREEN
import com.mu.tote2026.presentation.utils.Route.PROFILE_SCREEN

fun NavController.navigateToProfile(
    //email: String
) {
    //navigate("$PROFILE_SCREEN/$email") {
    navigate(PROFILE_SCREEN) {
        popUpTo(AUTH_SCREEN) {
            //inclusive = true
        }
    }
}