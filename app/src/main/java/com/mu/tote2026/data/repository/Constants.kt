package com.mu.tote2026.data.repository

import com.mu.tote2026.domain.model.GamblerModel

var CURRENT_ID = ""
var GAMBLER = GamblerModel()

const val GROUP_GAMES_COUNT = 36
const val PLAYOFF_GAMES_COUNT = 15

const val FOLDER_GAMBLER_PHOTO = "gambler_photo"

object Result {
    const val WIN = "win"
    const val DRAW = "draw"
    const val DEFEAT = "defeat"
}

object Collections {
    const val EMAILS = "emails"
    const val GROUPS = "groups"
    const val TEAMS = "teams"
    const val GAMES = "games"
    const val STAKES = "stakes"
    const val GAMBLERS = "gamblers"
    const val COMMON = "common"
    const val WINNERS = "winners"
}
object Errors {
    const val CREATE_USER_WITH_EMAIL_AND_PASSWORD_FUNCTION_EXECUTING_ERROR = "Ошибка выполнения функции createUserWithEmailAndPassword"
    const val SIGN_IN_WITH_EMAIL_AND_PASSWORD_FUNCTION_EXECUTING_ERROR = "Ошибка выполнения функции signInWithEmailAndPassword"
    const val ERROR_UNAUTHORIZED_EMAIL = "Неразрешённый email"
    const val ERROR_NEW_USER_IS_NOT_CREATED = "Новый пользователь не создан"
    const val GAMBLER_DOCUMENT_WRITE_ERROR= "Ошибка записи документа игрока"
    const val ERROR_USER_WAS_DELETED= "Учётка пользователя удалена"
    const val USER_DELETE_ERROR= "Ошибка при удалении пользователя"
    const val GAMBLER_PHOTO_URL_GET_ERROR = "Ошибка получения url фото участника после сохранения"
    const val GAMBLER_PHOTO_SAVE_ERROR = "Ошибка сохранения фото участника"
    const val GAMBLER_SAVE_ERROR = "Ошибка сохранения данных участника"
    const val GAME_SUM_GET_ERROR = "Ошибка чтения общих призовой суммы за игру"
    const val COMMON_PARAMS_SAVE_ERROR = "Ошибка сохранения общих параметров"
    const val WINNER_SAVE_ERROR = "Ошибка сохранения выигрыша победителя"
}