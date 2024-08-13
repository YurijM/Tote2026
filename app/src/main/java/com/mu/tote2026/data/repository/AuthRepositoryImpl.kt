package com.mu.tote2026.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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

        auth.createUserWithEmailAndPassword(
            email,
            password
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                /*val user = auth.currentUser

                if (user != null) {
                    GAMBLER = GamblerModel(
                        gamblerId = user.uid,
                        email = email
                    )

                    firestore.reference
                        .child(NODE_GAMBLERS)
                        .child(user.uid)
                        .setValue(GAMBLER)

                    CURRENT_ID = user.uid

                    trySend(UiState.Success(true))
                } else {
                    trySend(UiState.Error(ERROR_NEW_GAMBLER_IS_NOT_CREATED))
                }*/
            } else {
                //trySend(UiState.Error(ERROR_FUN_CREATE_USER_WITH_EMAIL_AND_PASSWORD))
            }
        }.addOnFailureListener {
            //trySend(UiState.Error(it.message ?: ERROR_FUN_CREATE_GAMBLER))
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

    override fun getCurrentGamblerId()  = auth.currentUser?.uid ?: ""
}