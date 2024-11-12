package com.mu.tote2026.presentation.navigation.destination.rating

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.navigation.Destinations.RatingDestination
import com.mu.tote2026.presentation.screen.rating.RatingScreen

fun NavGraphBuilder.rating() {
    composable<RatingDestination> {
        RatingScreen()
    }
}