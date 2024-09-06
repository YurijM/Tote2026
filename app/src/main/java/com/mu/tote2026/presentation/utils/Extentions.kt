package com.mu.tote2026.presentation.utils

import android.util.Log
import com.mu.tote2026.domain.model.GamblerModel
import com.mu.tote2026.presentation.utils.Errors.FIELD_CONTAINS_LESS_THAN_N_CHARS
import com.mu.tote2026.presentation.utils.Errors.FIELD_IS_EMPTY
import com.mu.tote2026.presentation.utils.Errors.INCORRECT_EMAIL
import com.mu.tote2026.presentation.utils.Errors.PASSWORDS_DO_NOT_MATCH

fun toLog(message: String) {
    Log.d(DEBUG_TAG, message)
}

fun String.withParam(param: String) = this.replace("%_%", param)

fun checkIsFieldEmpty(value: String?): String {
    return if ((value != null) && value.isBlank()) FIELD_IS_EMPTY
    else ""
}

fun checkEmail(email: String?): String {
    return if (email != null) {
        when {
            email.isBlank() -> FIELD_IS_EMPTY

            !android.util.Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches() -> INCORRECT_EMAIL

            else -> ""
        }
    } else ""
}

fun checkPassword(password: String?, passwordConfirm: String?): String {
    return if (password != null) {
        when {
            password.isBlank() -> FIELD_IS_EMPTY

            password.length < MIN_PASSWORD_LENGTH ->
                FIELD_CONTAINS_LESS_THAN_N_CHARS.withParam(MIN_PASSWORD_LENGTH.toString())

            !passwordConfirm.isNullOrBlank() &&
                    (passwordConfirm.length >= MIN_PASSWORD_LENGTH) &&
                    (password != passwordConfirm) -> PASSWORDS_DO_NOT_MATCH

            else -> ""
        }
    } else ""
}

fun errorTranslate(error: String): String = translateList.find { it.eng == error }?.rus ?: error

fun GamblerModel.checkProfile(): Boolean = this.nickname.isNotBlank() &&
        this.gender.isNotBlank() &&
        this.photoUrl.isNotBlank()