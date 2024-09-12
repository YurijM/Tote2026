package com.mu.tote2026.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.mu.tote2026.data.repository.Collections.GAMBLERS
import com.mu.tote2026.data.repository.Errors.GAMBLER_PHOTO_SAVE_ERROR
import com.mu.tote2026.data.repository.Errors.GAMBLER_PHOTO_URL_GET_ERROR
import com.mu.tote2026.data.repository.Errors.GAMBLER_SAVE_ERROR
import com.mu.tote2026.domain.model.GamblerModel
import com.mu.tote2026.domain.repository.GamblerRepository
import com.mu.tote2026.presentation.utils.toLog
import com.mu.tote2026.ui.common.UiState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class GamblerRepositoryImpl(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : GamblerRepository {
    /*override fun getGambler(email: String): Flow<UiState<GamblerModel>> = callbackFlow {
        trySend(UiState.Loading)

        firestore.collection(GAMBLERS).whereEqualTo("email", email).get()
            .addOnSuccessListener { task ->
                val gambler = task.toObjects(GamblerModel::class.java)[0]
                trySend(UiState.Success(gambler))
            }
            .addOnFailureListener { error ->
                trySend(UiState.Error(error.message ?: "getGambler: error is not defined"))
            }

        awaitClose {
            close()
        }
    }*/

    override fun getGambler(id: String): Flow<UiState<GamblerModel>> = callbackFlow {
        trySend(UiState.Loading)

        val listener = firestore.collection(GAMBLERS).document(id)   //.whereEqualTo("id", id)
            .addSnapshotListener { snapshot, exception ->
                if (snapshot != null) {
                    GAMBLER = snapshot.toObject(GamblerModel::class.java)!!
                    trySend(UiState.Success(GAMBLER))
                } else {
                    trySend(UiState.Error(exception?.message ?: exception.toString()))
                }
            }

        awaitClose {
            toLog("getGambler: awaitClose")
            listener.remove()
        }
    }

    override fun saveGambler(id: String, gambler: GamblerModel): Flow<UiState<GamblerModel>> = callbackFlow {
        trySend(UiState.Loading)

        firestore.collection(GAMBLERS).document(id).set(gambler)
            .addOnSuccessListener {
                trySend(UiState.Success(gambler))
            }
            .addOnFailureListener { error ->
                trySend(UiState.Error(error.message ?: GAMBLER_SAVE_ERROR))
            }

        awaitClose {
            toLog("saveGambler: awaitClose")
            close()
        }
    }

    //override fun saveGamblerPhoto(id: String, uri: Uri): Flow<UiState<String>> = callbackFlow {
    override fun saveGamblerPhoto(id: String, uri: ByteArray): Flow<UiState<String>> = callbackFlow {
        trySend(UiState.Loading)

        val path = storage.reference.child(FOLDER_GAMBLER_PHOTO).child(id)
        path.putBytes(uri)
        //path.putFile(uri)
            .addOnSuccessListener {
                path.downloadUrl
                    .addOnSuccessListener { uriFirebase ->
                        trySend(UiState.Success(uriFirebase.toString()))
                    }
                    .addOnFailureListener { error ->
                        trySend(UiState.Error(error.message ?: GAMBLER_PHOTO_URL_GET_ERROR))
                    }
            }
            .addOnFailureListener { error ->
                trySend(UiState.Error(error.message ?: GAMBLER_PHOTO_SAVE_ERROR))
            }

        awaitClose {
            toLog("saveGamblePhoto: awaitClose")
            close()
        }
    }
}