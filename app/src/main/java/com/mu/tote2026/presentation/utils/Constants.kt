package com.mu.tote2026.presentation.utils

import androidx.annotation.StringRes
import com.mu.tote2026.R
import com.mu.tote2026.presentation.navigation.Destinations

const val DEBUG_TAG = "tote2026"
const val YEAR_START = 2024
const val MIN_PASSWORD_LENGTH = 6

const val MALE = "мужской"
const val FEMALE = "женский"

const val START = "14.06.2025"
val GROUPS = listOf("A", "B", "C", "D", "E", "F", "1/8 финала", "1/4 финала", "1/2 финала", "Финал")
const val GROUPS_COUNT = 6

const val BRUSH = "brush"

const val KEY_ID = "id"
const val NEW_DOC = "new"

object Route {
    const val PROFILE_SCREEN = "profile_screen"
    const val ADMIN_MAIN_SCREEN = "admin_main_screen"
    const val ADMIN_EMAIL_LIST_SCREEN = "admin_email_list_screen"
    const val ADMIN_EMAIL_SCREEN = "admin_email_screen"
    const val ADMIN_GROUP_LIST_SCREEN = "admin_group_list_screen"
    const val ADMIN_GROUP_SCREEN = "admin_group_screen"
    const val ADMIN_GAMBLER_LIST_SCREEN = "admin_gambler_list_screen"
    const val ADMIN_GAMBLER_SCREEN = "admin_gambler_screen"
    const val ADMIN_TEAM_LIST_SCREEN = "admin_team_list_screen"
    const val ADMIN_GAME_LIST_SCREEN = "admin_game_list_screen"
    const val ADMIN_STAKE_LIST_SCREEN = "admin_stake_list_screen"
    const val ADMIN_COMMON_PARAMS_SCREEN = "admin_common_params_screen"
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
    const val ADD_GOAL_INCORRECT = "Количество мячей не может быть меньше, чем в основное время"
    const val PROGNOSIS_IS_ABSENT = "Прогноз\nотсутствует"
}

sealed class BottomNavItem(
    val titleId: Int,
    val iconId: Int,
    val destination: Destinations
) {
    data object RatingItem : BottomNavItem(
        titleId = R.string.rating,
        iconId = R.drawable.ic_rating,
        destination = Destinations.RatingDestination
    )

    data object StakesItem : BottomNavItem(
        titleId = R.string.stakes,
        iconId = R.drawable.ic_ruble,
        destination = Destinations.StakesDestination
    )

    data object PrognosisItem : BottomNavItem(
        titleId = R.string.prognosis,
        iconId = R.drawable.ic_prognosis,
        destination = Destinations.PrognosisDestination
    )

    data object GamesItem : BottomNavItem(
        titleId = R.string.games,
        iconId = R.drawable.ic_games,
        destination = Destinations.GamesDestination
    )
}

sealed class AdminNavItem(
    @StringRes val titleId: Int,
    val route: String
) {
    data object AdminEmailItem : AdminNavItem(
        titleId = R.string.admin_email_list,
        route = Route.ADMIN_EMAIL_LIST_SCREEN
    )

    data object AdminGamblerItem : AdminNavItem(
        titleId = R.string.admin_gambler_list,
        route = Route.ADMIN_GAMBLER_LIST_SCREEN
    )

    data object AdminGroupItem : AdminNavItem(
        titleId = R.string.admin_group_list,
        route = Route.ADMIN_GROUP_LIST_SCREEN
    )

    data object AdminTeamItem : AdminNavItem(
        titleId = R.string.admin_team_list,
        route = Route.ADMIN_TEAM_LIST_SCREEN
    )

    data object AdminGameItem : AdminNavItem(
        titleId = R.string.admin_game_list,
        route = Route.ADMIN_GAME_LIST_SCREEN
    )

    data object AdminStakeItem : AdminNavItem(
        titleId = R.string.admin_stake_list,
        route = Route.ADMIN_STAKE_LIST_SCREEN
    )

    data object AdminCommonParamsItem : AdminNavItem(
        titleId = R.string.admin_prize_fund,
        route = Route.ADMIN_COMMON_PARAMS_SCREEN
    )

    data object AdminFinishItem : AdminNavItem(
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