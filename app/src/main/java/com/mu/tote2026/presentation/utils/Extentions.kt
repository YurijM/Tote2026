package com.mu.tote2026.presentation.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import com.mu.tote2026.domain.model.GamblerModel
import com.mu.tote2026.presentation.utils.Errors.FIELD_CAN_NOT_BE_EMPTY
import com.mu.tote2026.presentation.utils.Errors.FIELD_CONTAINS_LESS_THAN_N_CHARS
import com.mu.tote2026.presentation.utils.Errors.INCORRECT_EMAIL
import com.mu.tote2026.presentation.utils.Errors.PASSWORDS_DO_NOT_MATCH
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun toLog(message: String) {
    Log.d(DEBUG_TAG, message)
}

fun String.withParam(param: String) = this.replace("%_%", param)

fun checkIsFieldEmpty(value: String?): String {
    return if ((value != null) && value.isBlank()) FIELD_CAN_NOT_BE_EMPTY
    else ""
}

fun checkEmail(email: String?): String {
    return if (email != null) {
        when {
            email.isBlank() -> FIELD_CAN_NOT_BE_EMPTY

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
            password.isBlank() -> FIELD_CAN_NOT_BE_EMPTY

            password.length < MIN_PASSWORD_LENGTH ->
                FIELD_CONTAINS_LESS_THAN_N_CHARS.withParam(MIN_PASSWORD_LENGTH.toString())

            !passwordConfirm.isNullOrBlank() &&
                    (passwordConfirm.length >= MIN_PASSWORD_LENGTH) &&
                    (password != passwordConfirm) -> PASSWORDS_DO_NOT_MATCH

            else -> ""
        }
    } else ""
}

fun bitmapToByteArray(
    context: Context,
    uri: Uri
): ByteArray {
    val inputStream = context.contentResolver.openInputStream(uri)
    val bitmap = BitmapFactory.decodeStream(inputStream)
    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos)
    return baos.toByteArray()
}

fun errorTranslate(error: String): String = translateList.find { it.eng == error }?.rus ?: error

fun GamblerModel.checkProfile(): Boolean = this.nickname.isNotBlank() &&
        this.gender.isNotBlank() &&
        this.photoUrl.isNotBlank()

fun convertDateTimeToTimestamp(datetime: String, toLocale: Boolean = false): String {
    val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    if (toLocale) simpleDateFormat.timeZone = TimeZone.getTimeZone("Europe/Moscow")
    val date = simpleDateFormat.parse(datetime)

    return date?.time.toString()
}

fun String.asDateTime(withSeconds: Boolean = false, toLocale: Boolean = false): String {
    val format = if (withSeconds) {
        "dd.MM.y HH:mm:ss"
    } else {
        "dd.MM.y HH:mm"
    }
    val formatter = SimpleDateFormat(format, Locale.getDefault())

    if (toLocale) formatter.timeZone = TimeZone.getTimeZone("Europe/Moscow")

    return try {
        formatter.format(Date(this.toLong()))
    } catch (e: Exception) {
        toLog("Ошибка asDateTime ${e.message}")
        ""
    }
}
