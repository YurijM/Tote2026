package com.mu.tote2026.presentation.navigation.destination.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.screen.auth.AuthScreen
import com.mu.tote2026.presentation.utils.Route.AUTH_SCREEN

fun NavGraphBuilder.auth(
    onSignUpClick: () -> Unit,
    onSignInClick: () -> Unit,
) {
    composable(AUTH_SCREEN) {
        AuthScreen(
            onSignUpClick = onSignUpClick,
            onSignInClick = onSignInClick
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

/*fun NavController.navigateToSignIn() {
    navigate(Constants.Routes.SIGN_IN_SCREEN) {
        popUpTo(Constants.Routes.AUTH_SCREEN) *//*{
            inclusive = true
        }*//*
    }
}*/
