package com.mu.tote2026.data.repository

object Collections {
    const val EMAILS = "emails"
    const val GAMBLERS = "gamblers"
}
object Errors {
    const val CREATE_USER_WITH_EMAIL_AND_PASSWORD_FUNCTION_EXECUTING_ERROR = "Ошибка выполнения функции createUserWithEmailAndPassword"
    const val SIGN_IN_WITH_EMAIL_AND_PASSWORD_FUNCTION_EXECUTING_ERROR = "Ошибка выполнения функции " +
            "signInWithEmailAndPassword"
    const val NEW_USER_IS_NOT_CREATED_ERROR = "Новый пользователь не создан"
    const val GAMBLER_DOCUMENT_WRITE_ERROR= "Ошибка записи документа игрока"
    const val USER_WAS_DELETED_ERROR= "Учётка пользователя удалена"
    const val USER_DELETE_ERROR= "Ошибка при удалении пользователя"
}