package com.mu.tote2026.data.repository

import com.google.firebase.firestore.FieldValue.arrayRemove
import com.google.firebase.firestore.FieldValue.arrayUnion
import com.google.firebase.firestore.FirebaseFirestore
import com.mu.tote2026.data.repository.Collections.GAMES
import com.mu.tote2026.domain.model.GameModel
import com.mu.tote2026.domain.model.GroupTeamResultModel
import com.mu.tote2026.domain.model.StakeModel
import com.mu.tote2026.domain.repository.GameRepository
import com.mu.tote2026.presentation.utils.BRUSH
import com.mu.tote2026.presentation.utils.GROUPS
import com.mu.tote2026.presentation.utils.GROUPS_COUNT
import com.mu.tote2026.presentation.utils.toLog
import com.mu.tote2026.ui.common.UiState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.lang.System.currentTimeMillis

class GameRepositoryImpl(
    private val firestore: FirebaseFirestore
) : GameRepository {
    override fun getGameList(): Flow<UiState<List<GameModel>>> = callbackFlow {
        trySend(UiState.Loading)

        val listener = firestore.collection(GAMES)
            .addSnapshotListener { value, error ->
                if (value != null) {
                    val games = value.toObjects(GameModel::class.java)
                    trySend(UiState.Success(games))
                } else if (error != null) {
                    trySend(UiState.Error(error.message ?: error.toString()))
                } else {
                    trySend(UiState.Error("getGameList: error is not defined"))
                }
            }

        awaitClose {
            toLog("getGameList: listener remove")
            listener.remove()
            close()
        }
    }

    override fun getGame(id: String): Flow<UiState<GameModel>> {
        TODO("Not yet implemented")
    }

    override fun saveGame(game: GameModel): Flow<UiState<GameModel>> = callbackFlow {
        trySend(UiState.Loading)

        firestore.collection(GAMES).document(game.id).set(game)
            .addOnSuccessListener {
                trySend(UiState.Success(game))
            }
            .addOnFailureListener { error ->
                trySend(UiState.Error(error.message ?: "saveGame: error is not defined"))
            }

        awaitClose {
            toLog("saveGame awaitClose")
            close()
        }
    }

    override fun deleteGame(id: String): Flow<UiState<Boolean>> = callbackFlow {
        trySend(UiState.Loading)

        firestore.collection(GAMES).document(id).delete()
            .addOnSuccessListener {
                trySend(UiState.Success(true))
            }
            .addOnFailureListener { error ->
                trySend(UiState.Error(error.message ?: "deleteGame: error is not defined"))
            }

        awaitClose {
            toLog("deleteGame awaitClose")
            close()
        }
    }

    override fun getGroupTeamResult(): Flow<UiState<Map<String, List<GroupTeamResultModel>>>> = callbackFlow {
        trySend(UiState.Loading)

        val listener = firestore.collection(GAMES)
            .addSnapshotListener { value, error ->
                if (value != null) {
                    val games = value.toObjects(GameModel::class.java)
                    val groupResult = mutableMapOf<String, List<GroupTeamResultModel>>()

                    for (groupId in 1..GROUPS_COUNT) {
                        val groupGames = games.filter { it.groupId.toInt() == groupId }
                        val resultList = getGroupTeamResult(groupGames)

                        groupResult[GROUPS[groupId - 1]] = resultList
                    }
                    trySend(UiState.Success(groupResult))
                } else if (error != null) {
                    trySend(UiState.Error(error.message ?: error.toString()))
                } else {
                    trySend(UiState.Error("getGroupTeamResult: error is not defined"))
                }
            }

        awaitClose {
            toLog("getGroupTeamResult: listener remove")
            listener.remove()
            close()
        }
    }

    override fun getGamblerStakes(gamblerId: String): Flow<UiState<List<GameModel>>> = callbackFlow {
        trySend(UiState.Loading)

        val listener = firestore.collection(GAMES)
            .addSnapshotListener { value, error ->
                if (value != null) {
                    val games = value.toObjects(GameModel::class.java)
                    games.removeAll { game -> game.start.toLong() < currentTimeMillis() }
                    games.forEachIndexed { index, game ->
                        val stakes = game.stakes.toMutableList()
                        stakes.removeAll { stake -> stake.gamblerId != gamblerId }
                        games[index] = game.copy(stakes = stakes)
                    }
                    games.sortBy { game -> game.id.toInt() }

                    trySend(UiState.Success(games))
                } else if (error != null) {
                    trySend(UiState.Error(error.message ?: error.toString()))
                } else {
                    trySend(UiState.Error("getGamblerStakes: error is not defined"))
                }
            }

        awaitClose {
            toLog("getGamblerStakes: listener remove")
            listener.remove()
            close()
        }
    }

    override fun getGamblerGameStake(gameId: String, gamblerId: String): Flow<UiState<GameModel>> = callbackFlow {
        trySend(UiState.Loading)

        firestore.collection(GAMES).document(gameId).get()
            .addOnSuccessListener { task ->
                var game = task.toObject(GameModel::class.java) ?: GameModel(gameId)
                val stakes = game.stakes.toMutableList()
                stakes.removeAll { stake -> stake.gamblerId != gamblerId }
                game = game.copy(stakes = stakes)

                trySend(UiState.Success(game))
            }
            .addOnFailureListener { error ->
                trySend(UiState.Error(error.message ?: "getGamblerStake: error is not defined"))
            }

        awaitClose {
            toLog("getGamblerStake: awaitClose")
            close()
        }
    }

    private fun getScore(result: GroupTeamResultModel, goal1: Int, goal2: Int): GroupTeamResultModel {
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

    override fun saveStake(oldStake: StakeModel, newStake: StakeModel): Flow<UiState<Boolean>> = callbackFlow {
        trySend(UiState.Loading)

        val game = firestore.collection(GAMES).document(oldStake.gameId)

        game.update("stakes", arrayRemove(oldStake))
            .addOnSuccessListener {
                game.update("stakes", arrayUnion(newStake))
                    .addOnSuccessListener {
                        trySend(UiState.Success(true))
                    }
                    .addOnFailureListener { error ->
                        trySend(UiState.Error(error.message ?: "saveStake->arrayUnion: error is not defined"))
                    }
            }
            .addOnFailureListener { error ->
                trySend(UiState.Error(error.message ?: "saveStake->arrayRemove: error is not defined"))
            }

        awaitClose {
            toLog("saveStake awaitClose")
            close()
        }
    }

    private fun getGroupTeamResult(groupGames: List<GameModel>): List<GroupTeamResultModel> {
        val resultList = mutableListOf<GroupTeamResultModel>()
        for (itemNo in 1..4) {
            val teamResult = groupGames
                .filter { it.team1ItemNo.toInt() == itemNo || it.team2ItemNo.toInt() == itemNo }
                .sortedBy {
                    if (it.team1ItemNo.toInt() == itemNo)
                        it.team2ItemNo
                    else
                        it.team1ItemNo
                }

            resultList.add(getTeamResult(teamResult, itemNo))
        }

        calcTeamPlace(resultList)

        return resultList
    }

    private fun getTeamResult(teamResult: List<GameModel>, itemNo: Int): GroupTeamResultModel {
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
                    score = "${game.goal1}: ${game.goal2}"
                    getScore(result, game.goal1.toInt(), game.goal2.toInt())
                } else {
                    score = "${game.goal2}: ${game.goal1}"
                    getScore(result, game.goal2.toInt(), game.goal1.toInt())
                }
            }

            result = setBrush(result, itemNo)
            result = setScore(result, secondItemNo, score)
        }

        return result
    }

    private fun setBrush(result: GroupTeamResultModel, teamNo: Int): GroupTeamResultModel {
        return when (teamNo) {
            1 -> result.copy(score1 = BRUSH)
            2 -> result.copy(score2 = BRUSH)
            3 -> result.copy(score3 = BRUSH)
            4 -> result.copy(score4 = BRUSH)
            else -> result
        }
    }

    private fun setScore(result: GroupTeamResultModel, teamNo: Int, score: String): GroupTeamResultModel {
        return when (teamNo) {
            1 -> result.copy(score1 = score)
            2 -> result.copy(score2 = score)
            3 -> result.copy(score3 = score)
            4 -> result.copy(score4 = score)
            else -> result
        }
    }

    private fun calcTeamPlace(resultTeams: MutableList<GroupTeamResultModel>) {
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
}