package com.mu.tote2026.presentation.utils

const val DEBUG_TAG = "tote2026"
const val MIN_PASSWORD_LENGTH = 6

object Route {
    const val SPLASH_SCREEN = "splash_screen"
    const val AUTH_SCREEN = "auth_screen"
    const val SIGN_IN_SCREEN = "sign_in_screen"
    const val TEST_SCREEN = "test_screen"
}

object Errors {
    const val FIELD_IS_EMPTY = "Поле не заполнено"
    const val FIELD_CONTAINS_LESS_THAN_N_CHARS = "Поле содержит меньше %_% символов"
    const val PASSWORDS_DO_NOT_MATCH = "Значения паролей не совпадают"
    const val INCORRECT_EMAIL = "Некорректное значение email"
    const val UNAUTHORIZED_EMAIL = "Неразрешённый email"
}
