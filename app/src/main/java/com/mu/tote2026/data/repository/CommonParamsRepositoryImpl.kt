package com.mu.tote2026.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.mu.tote2026.data.repository.Collections.COMMON
import com.mu.tote2026.domain.model.CommonParamsModel
import com.mu.tote2026.domain.repository.CommonParamsRepository
import com.mu.tote2026.presentation.utils.toLog
import com.mu.tote2026.ui.common.UiState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class CommonParamsRepositoryImpl(
    private val firestore: FirebaseFirestore
) : CommonParamsRepository {
    override fun getCommonParams(): Flow<UiState<CommonParamsModel>> = callbackFlow {
        trySend(UiState.Loading)

        firestore.collection(COMMON).document(COMMON).get()
            .addOnSuccessListener { task ->
                val commonParams = task.toObject(CommonParamsModel::class.java) ?: CommonParamsModel()
                trySend(UiState.Success(commonParams))
            }
            .addOnFailureListener { error ->
                trySend(UiState.Error("getCommonParams: ${error.message ?: "error is not defined"}"))
            }

        awaitClose {
            toLog("getCommonParams: awaitClose")
            close()
        }
    }

    override fun saveCommonParams(commonParams: CommonParamsModel): Flow<UiState<CommonParamsModel>> = callbackFlow {
        trySend(UiState.Loading)

        firestore.collection(COMMON).document(COMMON).set(commonParams)
            .addOnSuccessListener {
                trySend(UiState.Success(commonParams))
            }
            .addOnFailureListener { error ->
                trySend(UiState.Error("saveCommonParams: ${error.message ?: "error is not defined"}"))
            }

        awaitClose {
            toLog("saveCommonParams: awaitClose")
            close()
        }
    }

}
