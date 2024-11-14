package com.mu.tote2026.presentation.navigation.destination.rating

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.navigation.Destinations.GamblerPhotoDestination
import com.mu.tote2026.presentation.navigation.Destinations.RatingDestination
import com.mu.tote2026.presentation.screen.rating.RatingScreen

fun NavGraphBuilder.rating(
    toGamblerPhoto: (GamblerPhotoDestination) -> Unit
) {
    composable<RatingDestination> {
        RatingScreen(
            toGamblerPhoto = toGamblerPhoto
        )
    }
}