package com.mu.tote2026.presentation.navigation.destination.test

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.screen.TestScreen
import com.mu.tote2026.presentation.screen.auth.signIn.SignInScreen
import com.mu.tote2026.presentation.utils.Route.TEST_SCREEN

fun NavGraphBuilder.test() {
    composable(TEST_SCREEN) {
        TestScreen()
    }
}