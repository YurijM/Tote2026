package com.mu.tote2026.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.mu.tote2026.data.repository.Collections.TEAMS
import com.mu.tote2026.domain.model.TeamModel
import com.mu.tote2026.domain.repository.GameRepository
import com.mu.tote2026.presentation.utils.toLog
import com.mu.tote2026.ui.common.UiState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class GameRepositoryImpl(
    private val firestore: FirebaseFirestore
) : GameRepository {
    override fun getTeamList(): Flow<UiState<List<TeamModel>>> = callbackFlow {
        trySend(UiState.Loading)

        val listener = firestore.collection(TEAMS)
            .addSnapshotListener { value, error ->
                if (value != null) {
                    val teams = value.toObjects(TeamModel::class.java)
                    trySend(UiState.Success(teams))
                } else if (error != null) {
                    trySend(UiState.Error(error.message ?: error.toString()))
                } else {
                    trySend(UiState.Error("getTeamList: error is not defined"))
                }
            }

        awaitClose {
            toLog("getTeamList: listener remove")
            listener.remove()
        }
    }

    override fun getTeam(id: String): Flow<UiState<TeamModel>> = callbackFlow {
        trySend(UiState.Loading)

        firestore.collection(TEAMS).document(id).get()
            .addOnSuccessListener { task ->
                val team = task.toObject(TeamModel::class.java) ?: TeamModel(id)
                trySend(UiState.Success(team))
            }
            .addOnFailureListener { error ->
                trySend(UiState.Error(error.message ?: "getTeam: error is not defined"))
            }

        awaitClose {
            toLog("getTeam: awaitClose")
            close()
        }
    }

    override fun saveTeam(team: TeamModel): Flow<UiState<TeamModel>> = callbackFlow {
        trySend(UiState.Loading)

        /*var currentTeam = team
        val collection = firestore.collection(TEAMS)

        if (currentTeam.id == NEW_DOC)
            currentTeam = currentTeam.copy(id = collection.document().id)*/

        firestore.collection(TEAMS).document(team.team).set(team)
            .addOnSuccessListener {
                trySend(UiState.Success(team))
            }
            .addOnFailureListener { error ->
                trySend(UiState.Error(error.message ?: "saveTeam: error is not defined"))
            }

        awaitClose {
            toLog("saveTeam awaitClose")
            close()
        }
    }

    override fun deleteAllTeams(): Flow<UiState<Boolean>> = callbackFlow {
        trySend(UiState.Loading)

        firestore.collection(TEAMS).document().delete()
            .addOnSuccessListener {
                trySend(UiState.Success(true))
            }
            .addOnFailureListener { error ->
                trySend(UiState.Error(error.message ?: "deleteAllTeams: error is not defined"))
            }

        awaitClose {
            toLog("deleteAllTeams awaitClose")
            close()
        }
    }

    override fun deleteTeam(id: String): Flow<UiState<Boolean>> = callbackFlow {
        trySend(UiState.Loading)

        firestore.collection(TEAMS).document(id).delete()
            .addOnSuccessListener {
                trySend(UiState.Success(true))
            }
            .addOnFailureListener { error ->
                trySend(UiState.Error(error.message ?: "deleteTeam: error is not defined"))
            }

        awaitClose {
            toLog("deleteTeam awaitClose")
            close()
        }
    }
}