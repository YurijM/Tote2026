package com.mu.tote2026.domain.repository

import com.mu.tote2026.domain.model.GamblerModel
import com.mu.tote2026.ui.common.UiState
import kotlinx.coroutines.flow.Flow

interface GamblerRepository {
    fun getGambler(id: String): Flow<UiState<GamblerModel>>
    fun saveGambler(id: String, gambler: GamblerModel): Flow<UiState<GamblerModel>>
    //fun saveGamblerPhoto(id: String, uri: Uri): Flow<UiState<String>>
    fun saveGamblerPhoto(id: String, uri: ByteArray): Flow<UiState<String>>
}