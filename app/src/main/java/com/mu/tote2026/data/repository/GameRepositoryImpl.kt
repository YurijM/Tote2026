package com.mu.tote2026.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.mu.tote2026.data.repository.Collections.GAMES
import com.mu.tote2026.domain.model.GameModel
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

    override fun getGamblerStakes(gamblerId: String): Flow<UiState<List<GameModel>>> = callbackFlow {
        trySend(UiState.Loading)

        val listener = firestore.collection(GAMES)
            .addSnapshotListener { value, error ->
                if (value != null) {
                    val games = value.toObjects(GameModel::class.java)
                    games.removeAll { game -> game.start.toLong() < currentTimeMillis() }
                    for (game in games) {
                        game.stakes.toMutableList().removeAll { stake -> stake.gamblerId != gamblerId }
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
                val game = task.toObject(GameModel::class.java) ?: GameModel(gameId)
                game.stakes.toMutableList().removeAll { it.gamblerId != gamblerId }
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
}