package com.mu.tote2026.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object SplashRoute
@Serializable
object MainRoute
@Serializable
object AuthRoute
@Serializable
object SignInRoute
@Serializable
object SignUpRoute

@Serializable
object RatingRoute
@Serializable
object StakesRoute
@Serializable
data class StakeRoute(
    val gameId: String = "",
    val gamblerId: String = "",
)
@Serializable
object PrognosisRoute
@Serializable
object GamesRoute

