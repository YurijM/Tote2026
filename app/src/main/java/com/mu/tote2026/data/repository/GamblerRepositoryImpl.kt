package com.mu.tote2026.data.repository

import android.annotation.SuppressLint
import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.mu.tote2026.data.repository.Collections.COMMON
import com.mu.tote2026.data.repository.Collections.GAMBLERS
import com.mu.tote2026.data.repository.Errors.GAMBLER_PHOTO_SAVE_ERROR
import com.mu.tote2026.data.repository.Errors.GAMBLER_PHOTO_URL_GET_ERROR
import com.mu.tote2026.data.repository.Errors.GAMBLER_SAVE_ERROR
import com.mu.tote2026.data.repository.Errors.GAME_SUM_SAVE_ERROR
import com.mu.tote2026.domain.model.CommonParamsModel
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
    /*
            //firestore.collection(EMAILS).document().collection("stakes").whereGreaterThanOrEqualTo("gameNo", "2").get()
        firestore.collectionGroup("stakes").whereGreaterThanOrEqualTo("gameNo", "2").get()
            .addOnSuccessListener { task ->
                val stakes = task.toObjects(GameModel::class.java)
                for (stake in stakes) {
                    //toLog("stake gameNo => ${stake.gameNo}")
                }
            }
            .addOnFailureListener { error ->
                toLog("collectionGroup error => ${error.message}")
            }
     */
    override fun getGamblerList(): Flow<UiState<List<GamblerModel>>> = callbackFlow {
        trySend(UiState.Loading)

        val listener = firestore.collection(GAMBLERS)
            .addSnapshotListener { snapshot, exception ->
                if (snapshot != null) {
                    val gamblers = snapshot.toObjects(GamblerModel::class.java)
                    trySend(UiState.Success(gamblers))
                } else if (exception != null) {
                    trySend(UiState.Error(exception.message ?: exception.toString()))
                } else {
                    trySend(UiState.Error("getGamblerList: error is not defined"))
                }
            }
        awaitClose {
            toLog("getGamblerList: listener remove")
            listener.remove()
            close()
        }
    }

    override fun getGambler(id: String): Flow<UiState<GamblerModel>> = callbackFlow {
        trySend(UiState.Loading)

        val listener = firestore.collection(GAMBLERS).document(id)   //.whereEqualTo("id", id)
            .addSnapshotListener { snapshot, exception ->
                if (snapshot != null) {
                    //GAMBLER = snapshot.toObject(GamblerModel::class.java)!!
                    val gambler = snapshot.toObject(GamblerModel::class.java) ?: GamblerModel()
                    trySend(UiState.Success(gambler))
                } else {
                    trySend(UiState.Error(exception?.message ?: exception.toString()))
                }
            }

        awaitClose {
            toLog("getGambler: awaitClose")
            listener.remove()
            close()
        }
    }

    override fun saveGambler(gambler: GamblerModel): Flow<UiState<GamblerModel>> = callbackFlow {
        trySend(UiState.Loading)

        firestore.collection(GAMBLERS).document(gambler.id).set(gambler)
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

    override fun saveGamblerPhoto(id: String, uri: Uri): Flow<UiState<String>> = callbackFlow {
        trySend(UiState.Loading)

        val path = storage.reference.child(FOLDER_GAMBLER_PHOTO).child(id)
        path.putFile(uri)
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

    @SuppressLint("DefaultLocale")
    override fun saveGameSum(prizeFund: Int): Flow<UiState<CommonParamsModel>>  = callbackFlow {
        trySend(UiState.Loading)

        val winnersPrizeFund = (prizeFund.toDouble() * 2.0 / 9.0)
        val common = CommonParamsModel(
            prizeFund = prizeFund.toString(),
            /*groupPrizeFund = (prizeFund.toDouble() / 2.0).toString(),
            playoffPrizeFund = (prizeFund.toDouble() / 2.0).toString()*/
            groupPrizeFund = String.format("%.4f", (prizeFund.toDouble() / 3.0)),
            playoffPrizeFund = String.format("%.4f", (prizeFund.toDouble() / 3.0)),
            winnersPrizeFund = String.format("%.4f", winnersPrizeFund),
            place1PrizeFund = String.format("%.4f", (winnersPrizeFund / 2.0)),
            place2PrizeFund = String.format("%.4f", (winnersPrizeFund / 3.0)),
            place3PrizeFund = String.format("%.4f", (winnersPrizeFund / 6.0)),
            winnersPrizeFundByStake = String.format("%.4f", (prizeFund.toDouble() / 9.0)),
        )

        firestore.collection(COMMON).document(COMMON).set(common)
            .addOnSuccessListener {
                trySend(UiState.Success(common))
            }
            .addOnFailureListener { error ->
                trySend(UiState.Error(error.message ?: GAME_SUM_SAVE_ERROR))
            }

        awaitClose {
            toLog("saveGameSum: awaitClose")
            close()
        }
    }
}