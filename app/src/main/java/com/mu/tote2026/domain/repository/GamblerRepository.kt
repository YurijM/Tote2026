package com.mu.tote2026.domain.repository

import android.net.Uri
import com.mu.tote2026.domain.model.PrizeFundModel
import com.mu.tote2026.domain.model.GamblerModel
import com.mu.tote2026.domain.model.WinnerModel
import com.mu.tote2026.ui.common.UiState
import kotlinx.coroutines.flow.Flow

interface GamblerRepository {
    fun getGamblerList(): Flow<UiState<List<GamblerModel>>>
    fun getGambler(id: String): Flow<UiState<GamblerModel>>
    fun saveGambler(gambler: GamblerModel): Flow<UiState<GamblerModel>>
    fun saveGamblerPhoto(id: String, uri: Uri): Flow<UiState<String>>
    //fun saveGamblerPhoto(id: String, uri: ByteArray): Flow<UiState<String>>

    fun getPrizeFund(): Flow<UiState<PrizeFundModel>>
    fun savePrizeFund(prizeFund: Int): Flow<UiState<PrizeFundModel>>

    fun getWinners(): Flow<UiState<List<WinnerModel>>>
    fun saveWinner(winner: WinnerModel): Flow<UiState<WinnerModel>>
    fun deleteWinners(): Flow<UiState<Boolean>>
}