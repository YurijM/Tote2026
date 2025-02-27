package com.mu.tote2026.domain.repository

import com.mu.tote2026.domain.model.PrizeFundModel
import com.mu.tote2026.ui.common.UiState
import kotlinx.coroutines.flow.Flow

interface CommonRepository {
    fun getPrizeFund(): Flow<UiState<PrizeFundModel>>
    fun savePrizeFund(prizeFund: Int): Flow<UiState<PrizeFundModel>>
}