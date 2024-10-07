package com.mu.tote2026.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.mu.tote2026.data.repository.Collections.EMAILS
import com.mu.tote2026.domain.model.EmailModel
import com.mu.tote2026.domain.model.GameModel
import com.mu.tote2026.domain.repository.EmailRepository
import com.mu.tote2026.presentation.utils.NEW_DOC
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

        //firestore.collection(EMAILS).document().collection("stakes").whereGreaterThanOrEqualTo("gameNo", "2").get()
        firestore.collectionGroup("stakes").whereGreaterThanOrEqualTo("gameNo", "2").get()
            .addOnSuccessListener { task ->
                val stakes = task.toObjects(GameModel::class.java)
                for (stake in stakes) {
                    toLog("stake gameNo => ${stake.gameNo}")
                }
            }
            .addOnFailureListener { error ->
                toLog("collectionGroup error => ${error.message}")
            }
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
            close()
        }
    }

    override fun getEmail(id: String): Flow<UiState<EmailModel>> = callbackFlow {
        trySend(UiState.Loading)

        firestore.collection(EMAILS).document(id).get()
            .addOnSuccessListener { task ->
                val email = task.toObject(EmailModel::class.java) ?: EmailModel(id)
                trySend(UiState.Success(email))
            }
            .addOnFailureListener { error ->
                trySend(UiState.Error(error.message ?: "getEmail: error is not defined"))
            }

        awaitClose {
            toLog("getEmail: awaitClose")
            close()
        }
    }

    override fun saveEmail(email: EmailModel): Flow<UiState<EmailModel>> = callbackFlow {
        trySend(UiState.Loading)

        var currentEmail = email

        if (email.id == NEW_DOC) {
            currentEmail = currentEmail.copy(id = firestore.collection(EMAILS).document().id)
        }

        firestore.collection(EMAILS).document(currentEmail.id).set(currentEmail)
            .addOnSuccessListener {
                trySend(UiState.Success(currentEmail))
            }
            .addOnFailureListener { error ->
                trySend(UiState.Error(error.message ?: "saveEmail: error is not defined"))
            }

        awaitClose {
            toLog("saveEmail: awaitClose")
            close()
        }
    }

    override fun deleteEmail(id: String): Flow<UiState<Boolean>> = callbackFlow {
        trySend(UiState.Loading)

        firestore.collection(EMAILS).document(id).delete()
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