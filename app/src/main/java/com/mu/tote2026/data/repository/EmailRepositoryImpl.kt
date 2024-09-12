package com.mu.tote2026.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.mu.tote2026.data.repository.Collections.EMAILS
import com.mu.tote2026.domain.model.EmailModel
import com.mu.tote2026.domain.repository.EmailRepository
import com.mu.tote2026.presentation.utils.toLog
import com.mu.tote2026.ui.common.UiState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class EmailRepositoryImpl(
    private val firestore: FirebaseFirestore
) : EmailRepository {
    override fun getEmailList(): Flow<UiState<List<EmailModel>>> = callbackFlow {
        trySend(UiState.Loading)

        val listener = firestore.collection(EMAILS)
            .addSnapshotListener { snapshot, exception ->
                if (snapshot != null) {
                    val emails = snapshot.toObjects(EmailModel::class.java)
                    trySend(UiState.Success(emails))
                } else if (exception != null) {
                    trySend(UiState.Error(exception.message ?: exception.toString()))
                } else {
                    trySend(UiState.Error("getEmailList: error is not defined"))
                }
            }

        awaitClose {
            toLog("getEmailList: listener remove")
            listener.remove()
        }
    }

    override fun getEmail(email: String): Flow<UiState<EmailModel>> = callbackFlow {
        trySend(UiState.Loading)

        firestore.collection(EMAILS).document(email).get()
            .addOnSuccessListener {
                trySend(UiState.Success(EmailModel(email)))
            }
            .addOnFailureListener { error ->
                trySend(UiState.Error(error.message ?: "getEmail: error is not defined"))
            }

        awaitClose {
            toLog("getEmail: awaitClose")
            close()
        }
    }

    override fun saveEmail(email: String): Flow<UiState<EmailModel>> = callbackFlow {
        trySend(UiState.Loading)

        firestore.collection(EMAILS).document(email).set(EmailModel(email))
            .addOnSuccessListener {
                trySend(UiState.Success(EmailModel(email)))
            }
            .addOnFailureListener { error ->
                trySend(UiState.Error(error.message ?: "saveEmail: error is not defined"))
            }

        awaitClose {
            toLog("saveEmail: awaitClose")
            close()
        }
    }

    override fun deleteEmail(email: String): Flow<UiState<Boolean>> = callbackFlow {
        trySend(UiState.Loading)

        firestore.collection(EMAILS).document(email).delete()
            .addOnSuccessListener {
                trySend(UiState.Success(true))
            }
            .addOnFailureListener { error ->
                trySend(UiState.Error(error.message ?: "deleteEmail: error is not defined"))
            }

        awaitClose {
            toLog("deleteEmail: awaitClose")
            close()
        }
    }
}