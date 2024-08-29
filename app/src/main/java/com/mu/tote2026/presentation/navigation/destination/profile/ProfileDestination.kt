package com.mu.tote2026.presentation.navigation.destination.profile

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.screen.profile.ProfileScreen
import com.mu.tote2026.presentation.utils.Route.PROFILE_SCREEN

fun NavGraphBuilder.profile(
    toMain: () -> Unit,
    toAuth: () -> Unit
) {
    composable(PROFILE_SCREEN) {
        ProfileScreen(
            toMain = toMain,
            toAuth = toAuth
        )
    }
}