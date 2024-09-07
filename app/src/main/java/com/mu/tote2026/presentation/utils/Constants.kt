package com.mu.tote2026.presentation.utils

import com.mu.tote2026.R

const val DEBUG_TAG = "tote2026"
const val YEAR_START = 2024
const val MIN_PASSWORD_LENGTH = 6

const val MALE = "мужской"
const val FEMALE = "женский"

object Route {
    const val SPLASH_SCREEN = "splash_screen"
    const val AUTH_SCREEN = "auth_screen"
    const val SIGN_IN_SCREEN = "sign_in_screen"
    const val SIGN_UP_SCREEN = "sign_up_screen"
    const val MAIN_SCREEN = "main_screen"
    const val PROFILE_SCREEN = "profile_screen"
    const val RATING_SCREEN = "rating_screen"
    const val STAKE_LIST_SCREEN = "stake_list_screen"
    const val PROGNOSIS_SCREEN = "prognosis_screen"
    const val GAME_LIST_SCREEN = "game_list_screen"
    const val TEST_SCREEN = "test_screen"
}

object Errors {
    const val FIELD_IS_EMPTY = "Поле не заполнено"
    const val FIELD_CONTAINS_LESS_THAN_N_CHARS = "Поле содержит меньше %_% символов"
    const val PASSWORDS_DO_NOT_MATCH = "Значения паролей не совпадают"
    const val INCORRECT_EMAIL = "Некорректное значение email"
    const val UNAUTHORIZED_EMAIL = "Неразрешённый email"
    const val ERROR_PROFILE_IS_EMPTY = "Профиль не заполнен"
}

sealed class BottomNavItem(
    val titleId: Int,
    val iconId: Int,
    val route: String
) {
    data object RatingItem: BottomNavItem(
        titleId = R.string.rating,
        iconId = R.drawable.ic_rating,
        route = Route.RATING_SCREEN
    )
    data object StakeItem: BottomNavItem(
        titleId = R.string.stakes,
        iconId = R.drawable.ic_ruble,
        route = Route.STAKE_LIST_SCREEN
    )
    data object PrognosisItem: BottomNavItem(
        titleId = R.string.prognosis,
        iconId = R.drawable.ic_prognosis,
        route = Route.PROGNOSIS_SCREEN
    )
    data object GamesItem: BottomNavItem(
        titleId = R.string.games,
        iconId = R.drawable.ic_games,
        route = Route.GAME_LIST_SCREEN
    )
}

data class Translate(
    val eng: String = "",
    val rus: String = ""
)

val translateList = listOf(
    Translate(
        eng = "The email address is already in use by another account.",
        rus = "Участник с таким адресом уже зарегистрирован"
    ),
    Translate(
        eng = "The supplied auth credential is incorrect, malformed or has expired.",
        rus = "Email или пароль неверны"
    )
)