package com.mu.tote2026.presentation.navigation.destination.rating

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.mu.tote2026.presentation.navigation.Destinations.GamblerPhotoDestination
import com.mu.tote2026.presentation.screen.rating.GamblerPhotoScreen

fun NavGraphBuilder.gamblerPhoto() {
    composable<GamblerPhotoDestination> { backStackEntity ->
        val args = backStackEntity.toRoute<GamblerPhotoDestination>()
        GamblerPhotoScreen(args.photoUrl)
    }
}