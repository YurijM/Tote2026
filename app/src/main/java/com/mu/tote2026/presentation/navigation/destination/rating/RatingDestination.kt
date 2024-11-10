package com.mu.tote2026.presentation.navigation.destination.rating

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.navigation.RatingRoute
import com.mu.tote2026.presentation.screen.rating.RatingScreen
import com.mu.tote2026.presentation.utils.Route.RATING_SCREEN

fun NavGraphBuilder.rating() {
    /*composable(RATING_SCREEN) {
        RatingScreen()
    }*/
    composable<RatingRoute> {
        RatingScreen()
    }
}