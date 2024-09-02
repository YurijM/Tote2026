package com.mu.tote2026.presentation.navigation.destination.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.screen.auth.signup.SignUpScreen
import com.mu.tote2026.presentation.utils.Route.SIGN_UP_SCREEN

fun NavGraphBuilder.signUp(
    toProfile: () -> Unit
) {
    composable(SIGN_UP_SCREEN) {
        SignUpScreen(
            toProfile = toProfile
        )
    }
    /*composable(
        "$SIGN_UP_SCREEN/{$KEY_EMAIL}"
    ) {
        SignUpScreen(
            toProfile = toProfile
        )
    }*/
}