package com.mu.tote2026.domain.repository

import com.mu.tote2026.domain.model.GamblerModel
import com.mu.tote2026.ui.common.UiState
import kotlinx.coroutines.flow.Flow

interface GamblerRepository {
    fun getGambler(id: String): Flow<UiState<GamblerModel>>
}