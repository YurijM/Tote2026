package com.mu.tote2026.presentation.navigation.destination.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.navigation.Destinations.AuthDestination
import com.mu.tote2026.presentation.navigation.Destinations.SignInDestination
import com.mu.tote2026.presentation.navigation.Destinations.SignUpDestination
import com.mu.tote2026.presentation.screen.auth.AuthScreen

fun NavGraphBuilder.auth(
    toSignUp: () -> Unit,
    toSignIn: () -> Unit,
) {
    /*composable(AUTH_SCREEN) {
        AuthScreen(
            toSignUp = toSignUp,
            toSignIn = toSignIn
        )
    }*/
    composable<AuthDestination> {
        AuthScreen(
            toSignUp = toSignUp,
            toSignIn = toSignIn
        )
    }
}

fun NavController.navigateToSignUp() {
    /*navigate(SIGN_UP_SCREEN) {
        popUpTo(AUTH_SCREEN) {
            //inclusive = true
        }
    }*/
    navigate(SignUpDestination) {
        popUpTo(AuthDestination) {
            //inclusive = true
        }
    }
}

fun NavController.navigateToSignIn() {
    /*navigate(SIGN_IN_SCREEN) {
        popUpTo(AUTH_SCREEN) {
            //inclusive = true
        }
    }*/
    navigate(SignInDestination) {
        popUpTo(AuthDestination) {
            //inclusive = true
        }
    }
}
