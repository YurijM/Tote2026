package com.mu.tote2026.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.mu.tote2026.domain.model.GameModel
import com.mu.tote2026.domain.repository.GameRepository
import com.mu.tote2026.ui.common.UiState
import kotlinx.coroutines.flow.Flow

class GameRepositoryImpl(
    private val firestore: FirebaseFirestore
) : GameRepository {
    override fun saveGame(game: GameModel): Flow<UiState<GameModel>> {
        TODO("Not yet implemented")
    }

    override fun deleteGame(id: String): Flow<UiState<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun getGameList(): Flow<UiState<List<GameModel>>> {
        TODO("Not yet implemented")
    }

    override fun getGame(id: String): Flow<UiState<GameModel>> {
    TODO("Not yet implemented")
    }
}