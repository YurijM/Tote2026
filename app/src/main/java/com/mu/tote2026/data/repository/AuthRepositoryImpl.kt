package com.mu.tote2026.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mu.tote2026.data.repository.Collections.EMAILS
import com.mu.tote2026.data.repository.Collections.GAMBLERS
import com.mu.tote2026.data.repository.Errors.CREATE_USER_WITH_EMAIL_AND_PASSWORD_FUNCTION_EXECUTING_ERROR
import com.mu.tote2026.data.repository.Errors.ERROR_NEW_USER_IS_NOT_CREATED
import com.mu.tote2026.data.repository.Errors.ERROR_UNAUTHORIZED_EMAIL
import com.mu.tote2026.data.repository.Errors.ERROR_USER_WAS_DELETED
import com.mu.tote2026.data.repository.Errors.GAMBLER_DOCUMENT_WRITE_ERROR
import com.mu.tote2026.data.repository.Errors.SIGN_IN_WITH_EMAIL_AND_PASSWORD_FUNCTION_EXECUTING_ERROR
import com.mu.tote2026.data.repository.Errors.USER_DELETE_ERROR
import com.mu.tote2026.domain.model.EmailModel
import com.mu.tote2026.domain.model.GamblerModel
import com.mu.tote2026.domain.repository.AuthRepository
import com.mu.tote2026.ui.common.UiState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {
    override fun signUp(email: String, password: String): Flow<UiState<Boolean>> = callbackFlow {
        trySend(UiState.Loading)

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val user = auth.currentUser

                if (user != null) {
                    firestore.collection(GAMBLERS).document(user.uid).set(GamblerModel(id = user.uid, email = email))
                        .addOnSuccessListener {
                            CURRENT_ID = user.uid
                            trySend(UiState.Success(true))
                        }
                        .addOnFailureListener {
                            // Удаление из Authentication
                            val error = GAMBLER_DOCUMENT_WRITE_ERROR
                            user.delete()
                                .addOnSuccessListener {
                                    trySend(UiState.Error("$error \n $ERROR_USER_WAS_DELETED"))
                                }
                                .addOnFailureListener { e ->
                                    val message = "$error \n" + (e.message ?: USER_DELETE_ERROR)
                                    trySend(UiState.Error(message))
                                }
                        }
                } else {
                    trySend(UiState.Error(ERROR_NEW_USER_IS_NOT_CREATED))
                }
            }
            .addOnFailureListener { error ->
                trySend(UiState.Error(error.message ?: CREATE_USER_WITH_EMAIL_AND_PASSWORD_FUNCTION_EXECUTING_ERROR))
            }

        awaitClose {
            close()
        }
    }

    override fun signIn(email: String, password: String): Flow<UiState<Boolean>> = callbackFlow {
        trySend(UiState.Loading)

        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                CURRENT_ID = auth.currentUser?.uid.toString()
                trySend(UiState.Success(CURRENT_ID.isNotBlank()))
            }.addOnFailureListener { error ->
                trySend(UiState.Error(error.message ?: SIGN_IN_WITH_EMAIL_AND_PASSWORD_FUNCTION_EXECUTING_ERROR))
            }

        awaitClose {
            close()
        }
    }

    override fun getCurrentGamblerId() = auth.currentUser?.uid ?: ""

    override fun isEmailValid(email: String): Flow<UiState<Boolean>> = callbackFlow {
        trySend(UiState.Loading)

        /*firestore.collection(EMAILS).get()
            .addOnSuccessListener { task ->
                val emails = task.toObjects(EmailModel::class.java)
                val result = emails.find { it.email == email }
                if (result != null)
                    trySend(UiState.Success(true))
                else {
                    trySend(UiState.Success(false))
                    trySend(UiState.Error(UNAUTHORIZED_EMAIL))
                }
            }
            .addOnFailureListener { error ->
                trySend(UiState.Error(error.message ?: "isEmailValid: error is not defined"))
            }*/
        firestore.collection(EMAILS).whereEqualTo("email", email).get()
            .addOnSuccessListener { task ->
                val result = task.toObjects(EmailModel::class.java)
                if (result.isNotEmpty())
                    trySend(UiState.Success(true))
                else
                    trySend(UiState.Error(ERROR_UNAUTHORIZED_EMAIL))
            }
            .addOnFailureListener { error ->
                trySend(UiState.Error(error.message ?: "isEmailValid: error is not defined"))
            }

        awaitClose {
            close()
        }
    }

    /*override fun isEmailValid(email: String): Boolean {
        var isValid = false

        firestore.collection(EMAILS).addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                toLog("error: ${exception.message}")
            } else {
                val emails = snapshot?.toObjects(EmailModel::class.java) ?: emptyList()
                toLog("emails: $emails")
                toLog("email: ${emails.find { it.email == email }?.email}")
                isValid = !emails.find { it.email == email }?.email.isNullOrBlank()
                toLog("isValid: $isValid")
            }
        }

        return isValid
    }*/
}