package com.mu.tote2026.presentation.utils

import androidx.annotation.StringRes
import com.mu.tote2026.R

const val DEBUG_TAG = "tote2026"
const val YEAR_START = 2024
const val MIN_PASSWORD_LENGTH = 6

const val MALE = "мужской"
const val FEMALE = "женский"

const val KEY_ID = "id"
const val NEW_EMAIL = "new"

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
    const val ADMIN_MAIN_SCREEN = "admin_main_screen"
    const val ADMIN_EMAIL_LIST_SCREEN = "admin_email_list_screen"
    const val ADMIN_EMAIL_SCREEN = "admin_email_screen"
    const val ADMIN_GAMBLER_LIST_SCREEN = "admin_gambler_list_screen"
    const val ADMIN_GAMBLER_SCREEN = "admin_gambler_screen"
    const val ADMIN_TEAM_LIST_SCREEN = "admin_team_list_screen"
    const val ADMIN_GAME_LIST_SCREEN = "admin_game_list_screen"
    const val ADMIN_STAKE_LIST_SCREEN = "admin_stake_list_screen"
    const val ADMIN_FINISH_SCREEN = "admin_finish_screen"
    const val TEST_SCREEN = "test_screen"
}

object Errors {
    const val FIELD_CAN_NOT_BE_EMPTY = "Поле не может быть пустым"
    const val FIELD_CAN_NOT_NEGATIVE = "Значение не может быть отрицательным"
    const val FIELD_CONTAINS_LESS_THAN_N_CHARS = "Поле содержит меньше %_% символов"
    const val PASSWORDS_DO_NOT_MATCH = "Значения паролей не совпадают"
    const val INCORRECT_EMAIL = "Некорректное значение email"
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

sealed class AdminNavItem(
    @StringRes val titleId: Int,
    val route: String
) {
    data object AdminEmailItem: AdminNavItem(
        titleId = R.string.admin_email_list,
        route = Route.ADMIN_EMAIL_LIST_SCREEN
    )
    data object AdminGamblerItem: AdminNavItem(
        titleId = R.string.admin_gambler_list,
        route = Route.ADMIN_GAMBLER_LIST_SCREEN
    )
    data object AdminTeamItem: AdminNavItem(
        titleId = R.string.admin_team_list,
        route = Route.ADMIN_TEAM_LIST_SCREEN
    )
    data object AdminGameItem: AdminNavItem(
        titleId = R.string.admin_game_list,
        route = Route.ADMIN_GAME_LIST_SCREEN
    )
    data object AdminStakeItem: AdminNavItem(
        titleId = R.string.admin_stake_list,
        route = Route.ADMIN_STAKE_LIST_SCREEN
    )
    data object AdminFinishItem: AdminNavItem(
        titleId = R.string.admin_finish,
        route = Route.ADMIN_FINISH_SCREEN
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
    ),
    Translate(
        eng = "PERMISSION_DENIED: Missing or insufficient permissions.",
        rus = "Для данной операции отсутсвует разрешение"
    )
)