package com.mu.tote2026.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Destinations {
    @Serializable
    data object SplashRoute: Destinations()
    @Serializable
    object MainRoute: Destinations()
    @Serializable
    object AuthRoute: Destinations()
    @Serializable
    object SignInRoute: Destinations()
    @Serializable
    object SignUpRoute: Destinations()

    @Serializable
    object RatingRoute: Destinations()
    @Serializable
    object StakesRoute: Destinations()
    @Serializable
    data class StakeRoute(
        val gameId: String = "",
        val gamblerId: String = "",
    ): Destinations()
    @Serializable
    object PrognosisRoute: Destinations()
    @Serializable
    object GamesRoute: Destinations()
}