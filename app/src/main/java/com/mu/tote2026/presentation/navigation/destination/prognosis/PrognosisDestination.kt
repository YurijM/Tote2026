package com.mu.tote2026.presentation.navigation.destination.prognosis

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.navigation.Destinations.PrognosisDestination
import com.mu.tote2026.presentation.screen.prognosis.PrognosisListScreen

fun NavGraphBuilder.prognosis() {
    composable<PrognosisDestination> {
        PrognosisListScreen()
    }
}