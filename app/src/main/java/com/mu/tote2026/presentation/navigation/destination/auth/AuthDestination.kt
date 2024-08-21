package com.mu.tote2026.presentation.navigation.destination.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.screen.auth.AuthScreen
import com.mu.tote2026.presentation.utils.Route.AUTH_SCREEN
import com.mu.tote2026.presentation.utils.Route.SIGN_IN_SCREEN

fun NavGraphBuilder.auth(
    toSignUp: () -> Unit,
    toSignIn: () -> Unit,
) {
    composable(AUTH_SCREEN) {
        AuthScreen(
            toSignUp = toSignUp,
            toSignIn = toSignIn
        )
    }
}

/*fun NavController.navigateToSignUp() {
    navigate(Constants.Routes.SIGN_UP_SCREEN) {
        popUpTo(Constants.Routes.AUTH_SCREEN) *//*{
            inclusive = true
        }*//*
    }
}*/

fun NavController.navigateToSignIn() {
    navigate(SIGN_IN_SCREEN) {
        popUpTo(AUTH_SCREEN) {
            //inclusive = true
        }
    }
}
