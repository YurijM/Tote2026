package com.mu.tote2026.presentation.navigation.destination.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.navigation.Destinations.SignInDestination
import com.mu.tote2026.presentation.screen.auth.signIn.SignInScreen

fun NavGraphBuilder.signIn(
    toMain: () -> Unit
) {
    /*composable(SIGN_IN_SCREEN) {
        SignInScreen(
            toMain = toMain
        )
    }*/
    composable<SignInDestination> {
        SignInScreen(
            toMain = toMain
        )
    }
}