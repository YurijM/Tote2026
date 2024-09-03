package com.mu.tote2026.presentation.navigation.destination.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.screen.auth.signIn.SignInScreen
import com.mu.tote2026.presentation.utils.Route.SIGN_IN_SCREEN

fun NavGraphBuilder.signIn(
    toMain: () -> Unit,
    toProfile: () -> Unit
) {
    composable(SIGN_IN_SCREEN) {
        SignInScreen(
            toMain = toMain,
            toProfile = toProfile
        )
    }
}