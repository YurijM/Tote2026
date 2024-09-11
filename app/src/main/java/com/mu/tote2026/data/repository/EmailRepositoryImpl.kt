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
            toLog("getEmailList: awaitClose")
            listener.remove()
        }
    }

    override fun getEmail(email: String): Flow<UiState<EmailModel>> = callbackFlow {
        trySend(UiState.Loading)

        firestore.collection(EMAILS).whereEqualTo("email", email).get()
            .addOnSuccessListener { task ->
                toLog("task: $task")
                for (document in task) {
                    toLog("document: ${document.reference.parent.id}")
                }
                val emails = task.toObjects(EmailModel::class.java)
                trySend(UiState.Success(emails[0]))
            }
            .addOnFailureListener { error ->
                trySend(UiState.Error(error.message ?: "getEmail: error is not defined"))
            }

        awaitClose {
            toLog("getEmail: awaitClose")
            close()
        }
    }

    override fun saveEmail(docId: String, email: String): Flow<UiState<EmailModel>> = callbackFlow {
        trySend(UiState.Loading)

        awaitClose {
            toLog("saveEmail: awaitClose")
            close()
        }
    }

    override fun deleteEmail(docId: String): Flow<UiState<Boolean>>  = callbackFlow {
        trySend(UiState.Loading)

        firestore.collection(EMAILS).document(docId).delete()
            .addOnSuccessListener {
                toLog("deleteEmail -> docId: $docId")
                trySend(UiState.Success(true))
            }
            .addOnFailureListener { error ->
                toLog("deleteEmail -> error: $error")
                trySend(UiState.Error(error.message ?: "deleteEmail: error is not defined"))
            }

        awaitClose {
            toLog("deleteEmail: awaitClose")
            close()
        }
    }
}