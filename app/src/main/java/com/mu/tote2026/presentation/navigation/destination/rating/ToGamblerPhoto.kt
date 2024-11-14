package com.mu.tote2026.presentation.navigation.destination.rating

import androidx.navigation.NavController
import com.mu.tote2026.presentation.navigation.Destinations.GamblerPhotoDestination

fun NavController.navigateToGamblerPhoto(
    args: GamblerPhotoDestination
) {
    navigate(GamblerPhotoDestination(args.photoUrl))
}