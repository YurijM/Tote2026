package com.mu.tote2026.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.mu.tote2026.data.repository.Collections.COMMON
import com.mu.tote2026.data.repository.Collections.PRIZE_FUND
import com.mu.tote2026.data.repository.Errors.PRIZE_FUND_SAVE_ERROR
import com.mu.tote2026.domain.model.PrizeFundModel
import com.mu.tote2026.domain.repository.CommonRepository
import com.mu.tote2026.presentation.utils.toLog
import com.mu.tote2026.ui.common.UiState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class CommonRepositoryImpl(
    private val firestore: FirebaseFirestore
) : CommonRepository {

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
}
