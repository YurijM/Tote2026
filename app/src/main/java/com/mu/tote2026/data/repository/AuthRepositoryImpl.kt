package com.mu.tote2026.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mu.tote2026.data.repository.Collections.EMAILS
import com.mu.tote2026.data.repository.Errors.ERROR_FUN_SIGN_UP
import com.mu.tote2026.data.repository.Errors.ERROR_NEW_GAMBLER_IS_NOT_CREATED
import com.mu.tote2026.domain.model.Email
import com.mu.tote2026.domain.repository.AuthRepository
import com.mu.tote2026.presentation.utils.Errors.UNAUTHORIZED_EMAIL
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

        auth.createUserWithEmailAndPassword(
            email,
            password
        ).addOnSuccessListener {
            val user = auth.currentUser

            if (user != null) {
                trySend(UiState.Success(true))
            } else {
                trySend(UiState.Error(ERROR_NEW_GAMBLER_IS_NOT_CREATED))
            }
        }.addOnFailureListener {
            trySend(UiState.Error(it.message ?: ERROR_FUN_SIGN_UP))
        }

        awaitClose {
            close()
        }
    }

    override fun signIn(email: String, password: String): Flow<UiState<Boolean>> = callbackFlow {
        trySend(UiState.Loading)

        auth.signInWithEmailAndPassword(
            email,
            password
        ).addOnSuccessListener {
            val uid = auth.currentUser?.uid.toString()
            //CURRENT_ID = firebaseAuth.currentUser?.uid.toString()

            trySend(UiState.Success(uid.isNotBlank()))
            //trySend(UiState.Success(CURRENT_ID.isNotBlank()))
        }.addOnFailureListener {
            trySend(UiState.Error(it.message ?: "signIn: error is not defined"))
        }
        awaitClose {
            close()
        }
    }

    override fun getCurrentGamblerId() = auth.currentUser?.uid ?: ""

    override fun isEmailValid(email: String): Flow<UiState<Boolean>> = callbackFlow {
        trySend(UiState.Loading)

        firestore.collection(EMAILS).get()
            .addOnSuccessListener { task ->
                val emails = task.toObjects(Email::class.java)
                val result = emails.find { it.email == email }
                if (result != null)
                    trySend(UiState.Success(true))
                else {
                    trySend(UiState.Success(false))
                    trySend(UiState.Error(UNAUTHORIZED_EMAIL))
                }
            }
            .addOnFailureListener { e ->
                trySend(UiState.Error(e.message ?: "signIn: error is not defined"))
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
                val emails = snapshot?.toObjects(Email::class.java) ?: emptyList()
                toLog("emails: $emails")
                toLog("email: ${emails.find { it.email == email }?.email}")
                isValid = !emails.find { it.email == email }?.email.isNullOrBlank()
                toLog("isValid: $isValid")
            }
        }

        return isValid
    }*/
}