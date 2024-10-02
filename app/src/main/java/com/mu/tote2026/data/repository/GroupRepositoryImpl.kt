package com.mu.tote2026.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.mu.tote2026.data.repository.Collections.GROUPS
import com.mu.tote2026.domain.model.GroupModel
import com.mu.tote2026.domain.repository.GroupRepository
import com.mu.tote2026.presentation.utils.toLog
import com.mu.tote2026.ui.common.UiState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class GroupRepositoryImpl(
    private val firestore: FirebaseFirestore
) : GroupRepository {
    override fun getGroupList(): Flow<UiState<List<GroupModel>>> = callbackFlow {
        trySend(UiState.Loading)

        val listener = firestore.collection(GROUPS)
            .addSnapshotListener { snapshot, exception ->
                if (snapshot != null) {
                    val groups = snapshot.toObjects(GroupModel::class.java)
                    trySend(UiState.Success(groups))
                } else if (exception != null) {
                    trySend(UiState.Error(exception.message ?: exception.toString()))
                } else {
                    trySend(UiState.Error("getGroupList: error is not defined"))
                }
            }

        awaitClose {
            toLog("getGroupList: listener remove")
            listener.remove()
        }
    }

    override fun getGroup(id: String): Flow<UiState<GroupModel>>  = callbackFlow {
        trySend(UiState.Loading)

        firestore.collection(GROUPS).document(id).get()
            .addOnSuccessListener { task ->
                val group = task.toObject(GroupModel::class.java) ?: GroupModel()
                trySend(UiState.Success(group))
            }
            .addOnFailureListener { error ->
                trySend(UiState.Error(error.message ?: "getGroup: error is not defined"))
            }

        awaitClose {
            toLog("getGroup: awaitClose")
            close()
        }
    }

    override fun saveGroup(group: GroupModel): Flow<UiState<GroupModel>> = callbackFlow {
        trySend(UiState.Loading)

        firestore.collection(GROUPS).document(group.id.toString()).set(group)
            .addOnSuccessListener {
                trySend(UiState.Success(group))
            }
            .addOnFailureListener { error ->
                trySend(UiState.Error(error.message ?: "saveGroup: error is not defined"))
            }

        awaitClose {
            toLog("saveGroup: awaitClose")
            close()
        }
    }

    override fun deleteGroup(id: String): Flow<UiState<Boolean>> = callbackFlow {
        trySend(UiState.Loading)

        firestore.collection(GROUPS).document(id).delete()
            .addOnSuccessListener {
                trySend(UiState.Success(true))
            }
            .addOnFailureListener { error ->
                trySend(UiState.Error(error.message ?: "deleteGroup: error is not defined"))
            }

        awaitClose {
            toLog("deleteGroup: awaitClose")
            close()
        }
    }
}