package com.mu.tote2026.domain.repository

import com.mu.tote2026.domain.model.GameModel
import com.mu.tote2026.ui.common.UiState
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    fun getGameList(): Flow<UiState<List<GameModel>>>
    fun getGame(id: String): Flow<UiState<GameModel>>
    fun saveGame(game: GameModel): Flow<UiState<GameModel>>
    fun deleteGame(id: String): Flow<UiState<Boolean>>

    fun getGamblerStakes(gamblerId: String): Flow<UiState<List<GameModel>>>
}