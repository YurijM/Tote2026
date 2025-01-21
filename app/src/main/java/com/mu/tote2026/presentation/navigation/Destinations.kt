package com.mu.tote2026.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Destinations {
    @Serializable
    data object SplashDestination: Destinations()
    @Serializable
    data object MainDestination: Destinations()
    @Serializable
    data object AuthDestination: Destinations()
    @Serializable
    data object SignInDestination: Destinations()
    @Serializable
    data object SignUpDestination: Destinations()

    @Serializable
    data object RatingDestination: Destinations()
    @Serializable
    data class GamblerPhotoDestination(
        val photoUrl: String = ""
    ): Destinations()
    @Serializable
    data object StakesDestination: Destinations()
    @Serializable
    data class StakeDestination(
        val gameId: String = "",
        val gamblerId: String = "",
    ): Destinations()
    @Serializable
    data object PrognosisDestination: Destinations()
    @Serializable
    data object GamesDestination: Destinations()
    @Serializable
    data class GroupGamesDestination(
        val group: String = ""
    ): Destinations()
    @Serializable
    data class GameDestination(
        val id: String = ""
    ): Destinations()
}