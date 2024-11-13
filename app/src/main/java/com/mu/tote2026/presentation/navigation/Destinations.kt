package com.mu.tote2026.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Destinations {
    @Serializable
    data object SplashDestination: Destinations()
    @Serializable
    object MainDestination: Destinations()
    @Serializable
    object AuthDestination: Destinations()
    @Serializable
    object SignInDestination: Destinations()
    @Serializable
    object SignUpDestination: Destinations()

    @Serializable
    object RatingDestination: Destinations()
    @Serializable
    object StakesDestination: Destinations()
    @Serializable
    data class StakeDestination(
        val gameId: String = "",
        val gamblerId: String = "",
    ): Destinations()
    @Serializable
    object PrognosisDestination: Destinations()
    @Serializable
    object GamesDestination: Destinations()
    @Serializable
    data class GroupGamesDestination(val group: String): Destinations()
}