package com.mu.tote2026.presentation.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import com.mu.tote2026.domain.model.GamblerModel
import com.mu.tote2026.domain.model.GameModel
import com.mu.tote2026.domain.model.GroupTeamResultModel
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

fun String.asDate(toLocale: Boolean = false): String {
    val format = "dd.MM.y"
    val formatter = SimpleDateFormat(format, Locale.getDefault())

    if (toLocale) formatter.timeZone = TimeZone.getTimeZone("Europe/Moscow")

    return try {
        formatter.format(Date(this.toLong()))
    } catch (e: Exception) {
        toLog("Ошибка asDate ${e.message}")
        ""
    }
}

fun String.asTime(): String {
    val format = "HH:mm"

    val formatter = SimpleDateFormat(format, Locale.getDefault())

    return try {
        formatter.format(Date(this.toLong()))
    } catch (e: Exception) {
        toLog("Ошибка asTime ${e.message}")
        ""
    }
}

fun generateResult() : String {
    val goal1 = (0..3).random()
    val goal2 = (0..3).random()
    var result = "$goal1 : $goal2"
    if (goal1 == goal2) {
        val addGoal1 = (goal1..3).random()
        val addGoal2 = (goal2..3).random()
        result += ", доп.время $addGoal1 : $addGoal2"
        if (addGoal1 == addGoal2) {
            result += ", по пенальти ${(1..2).random()}"
        }
    }

    return result
}

fun getGroupTeamResult(groupGames: List<GameModel>): List<GroupTeamResultModel> {
    val resultList = mutableListOf<GroupTeamResultModel>()
    for (itemNo in 1..4) {
        val teamResult = groupGames
            .filter { it.team1ItemNo == itemNo.toString() || it.team2ItemNo == itemNo.toString() }
            .sortedBy {
                if (it.team1ItemNo == itemNo.toString())
                    it.team2ItemNo
                else
                    it.team1ItemNo
            }

        resultList.add(getTeamResult(teamResult, itemNo))
    }

    calcTeamPlace(resultList)

    return resultList
}

fun getTeamResult(teamResult: List<GameModel>, itemNo: Int): GroupTeamResultModel {
    var result = GroupTeamResultModel()

    teamResult.forEachIndexed { index, game ->
        val secondItemNo: Int
        val isFirstTeam = if (game.team1ItemNo.toInt() == itemNo) {
            secondItemNo = game.team2ItemNo.toInt()
            true
        } else {
            secondItemNo = game.team1ItemNo.toInt()
            false
        }
        var score = ""

        if (index == 0) {
            result = GroupTeamResultModel(
                group = game.group,
                team = if (isFirstTeam) game.team1 else game.team2,
                teamNo = itemNo,
            )
        }

        if (game.goal1.isNotBlank() && game.goal2.isNotBlank()) {
            result = if (isFirstTeam) {
                score = "${game.goal1}:${game.goal2}"
                getScore(result, game.goal1.toInt(), game.goal2.toInt())
            } else {
                score = "${game.goal2}:${game.goal1}"
                getScore(result, game.goal2.toInt(), game.goal1.toInt())
            }
        }

        result = setBrush(result, itemNo)
        result = setScore(result, secondItemNo, score)
    }

    return result
}

fun getScore(result: GroupTeamResultModel, goal1: Int, goal2: Int): GroupTeamResultModel {
    val balls1 = result.balls1 + goal1
    val balls2 = result.balls2 + goal2
    var win = result.win
    var draw = result.draw
    var defeat = result.defeat
    var points = result.points

    when {
        (goal1 > goal2) -> {
            win++
            points += 3
        }
        (goal1 == goal2) -> {
            draw++
            points += 1
        }
        else -> defeat++
    }
    return result.copy(
        balls1 = balls1,
        balls2 = balls2,
        win = win,
        draw = draw,
        defeat = defeat,
        points = points
    )
}

fun setBrush(result: GroupTeamResultModel, teamNo: Int): GroupTeamResultModel {
    return when (teamNo) {
        1 -> result.copy(score1 = BRUSH)
        2 -> result.copy(score2 = BRUSH)
        3 -> result.copy(score3 = BRUSH)
        4 -> result.copy(score4 = BRUSH)
        else -> result
    }
}

fun setScore(result: GroupTeamResultModel, teamNo: Int, score: String): GroupTeamResultModel {
    return when (teamNo) {
        1 -> result.copy(score1 = score)
        2 -> result.copy(score2 = score)
        3 -> result.copy(score3 = score)
        4 -> result.copy(score4 = score)
        else -> result
    }
}

fun calcTeamPlace(resultTeams: MutableList<GroupTeamResultModel>) {
    (0 until GROUPS_COUNT).forEach { groupNo ->
        val group = GROUPS[groupNo]
        resultTeams.filter { it.group == group }
            .sortedWith(
                compareByDescending<GroupTeamResultModel> { it.points }
                    .thenByDescending { (it.balls1 - it.balls2) }
                    .thenByDescending { it.balls1 }
            )
            .forEachIndexed { place, result ->
                if (place == 0 && result.points == 0) return@forEach

                val index = resultTeams.indexOf(resultTeams.find { it.team == result.team })

                resultTeams[index] = result.copy(place = place + 1)
            }
    }
}