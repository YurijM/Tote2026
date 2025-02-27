package com.mu.tote2026.data.repository

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.mu.tote2026.data.repository.Collections.COMMON
import com.mu.tote2026.data.repository.Collections.GAMBLERS
import com.mu.tote2026.data.repository.Collections.PRIZE_FUND
import com.mu.tote2026.data.repository.Collections.WINNERS
import com.mu.tote2026.data.repository.Errors.PRIZE_FUND_SAVE_ERROR
import com.mu.tote2026.data.repository.Errors.GAMBLER_PHOTO_SAVE_ERROR
import com.mu.tote2026.data.repository.Errors.GAMBLER_PHOTO_URL_GET_ERROR
import com.mu.tote2026.data.repository.Errors.GAMBLER_SAVE_ERROR
import com.mu.tote2026.data.repository.Errors.WINNER_SAVE_ERROR
import com.mu.tote2026.domain.model.PrizeFundModel
import com.mu.tote2026.domain.model.GamblerModel
import com.mu.tote2026.domain.model.WinnerModel
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

    override fun getPrizeFund(): Flow<UiState<PrizeFundModel>> = callbackFlow {
        trySend(UiState.Loading)

        firestore.collection(COMMON).document(PRIZE_FUND).get()
            .addOnSuccessListener { task ->
                val prizeFund = task.toObject(PrizeFundModel::class.java) ?: PrizeFundModel()
                trySend(UiState.Success(prizeFund))
            }
            .addOnFailureListener { error ->
                trySend(UiState.Error("getPrizeFund: ${error.message ?: "error is not defined"}"))
            }

        awaitClose {
            toLog("getPrizeFund: awaitClose")
            close()
        }
    }

    override fun savePrizeFund(prizeFund: Int): Flow<UiState<PrizeFundModel>>  = callbackFlow {
        trySend(UiState.Loading)

        //val winnersPrizeFund = (prizeFund.toDouble() * 2.0 / 9.0)
        val winnersPrizeFund = (prizeFund.toDouble() / 6.0)
        val commonPrizeFund = PrizeFundModel(
            prizeFund = prizeFund,
            groupPrizeFund = prizeFund.toDouble() / 3.0,
            playoffPrizeFund = prizeFund.toDouble() / 3.0,
            winnersPrizeFund = winnersPrizeFund,
            winnersPrizeFundByStake = winnersPrizeFund,
            //winnersPrizeFund = winnersPrizeFund,
            //place1PrizeFund = winnersPrizeFund / 2.0,
            //place2PrizeFund = winnersPrizeFund / 3.0,
            //place3PrizeFund = winnersPrizeFund / 6.0,
            //winnersPrizeFundByStake = prizeFund.toDouble() / 9.0,
        )

        firestore.collection(COMMON).document(PRIZE_FUND).set(commonPrizeFund)
            .addOnSuccessListener {
                trySend(UiState.Success(commonPrizeFund))
            }
            .addOnFailureListener { error ->
                trySend(UiState.Error( "savePrizeFund: ${error.message ?: PRIZE_FUND_SAVE_ERROR}"))
            }

        awaitClose {
            toLog("savePrizeFund: awaitClose")
            close()
        }
    }

    override fun getWinners(): Flow<UiState<List<WinnerModel>>> = callbackFlow {
        trySend(UiState.Loading)

        firestore.collection(WINNERS).get()
            .addOnSuccessListener { task ->
                val winners = task.toObjects(WinnerModel::class.java)
                trySend(UiState.Success(winners))
            }
            .addOnFailureListener { error ->
                trySend(UiState.Error("getWinners: ${error.message ?: "error is not defined"}"))
            }

        awaitClose {
            toLog("getWinners: awaitClose")
            close()
        }
    }

    override fun saveWinner(winner: WinnerModel): Flow<UiState<WinnerModel>> = callbackFlow {
        trySend(UiState.Loading)

        firestore.collection(WINNERS).document(winner.gamblerId).set(winner)
            .addOnSuccessListener {
                trySend(UiState.Success(winner))
            }
            .addOnFailureListener { error ->
                trySend(UiState.Error( "saveWinner: ${error.message ?: WINNER_SAVE_ERROR}"))
            }

        awaitClose {
            toLog("saveWinner: awaitClose")
            close()
        }
    }

    override fun deleteWinners(): Flow<UiState<Boolean>> = callbackFlow {
        trySend(UiState.Loading)

        firestore.collection(WINNERS).get()
            .addOnCompleteListener {
                for (document in it.result.documents) {
                    document.reference.delete()
                }
                trySend(UiState.Success(true))
            }

        awaitClose {
            toLog("deleteWinners: awaitClose")
            close()
        }
    }
}