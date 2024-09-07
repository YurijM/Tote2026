package com.mu.tote2026.presentation.navigation.destination.prognosis

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.screen.prognosis.PrognosisListScreen
import com.mu.tote2026.presentation.utils.Route.PROGNOSIS_SCREEN

fun NavGraphBuilder.prognosis() {
    composable(PROGNOSIS_SCREEN) {
        PrognosisListScreen()
    }
}