package com.mu.tote2026.data.repository

import com.google.firebase.firestore.FieldValue.arrayRemove
import com.google.firebase.firestore.FieldValue.arrayUnion
import com.google.firebase.firestore.FirebaseFirestore
import com.mu.tote2026.data.repository.Collections.GAMES
import com.mu.tote2026.domain.model.GameModel
import com.mu.tote2026.domain.model.StakeModel
import com.mu.tote2026.domain.repository.GameRepository
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

    override fun getGame(id: String): Flow<UiState<GameModel>> = callbackFlow {
        trySend(UiState.Loading)

        firestore.collection(GAMES).document(id).get()
            .addOnSuccessListener { task ->
                var game = task.toObject(GameModel::class.java) ?: GameModel()

                trySend(UiState.Success(game))
            }
            .addOnFailureListener { error ->
                trySend(UiState.Error(error.message ?: "getGame: error is not defined"))
            }

        awaitClose {
            toLog("getGame: awaitClose")
            close()
        }
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

    /*override fun getGroupTeamResult(): Flow<UiState<Map<String, List<GroupTeamResultModel>>>> = callbackFlow {
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
    }*/

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
}